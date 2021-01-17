package nagarro.jpa.controller;

import lombok.extern.slf4j.Slf4j;
import nagarro.jpa.entity.Statement;
import nagarro.jpa.entity.StatementQuery;
import nagarro.jpa.repository.StatementRepository;
import nagarro.jpa.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author alperkopuz
 * Statement Controller class responsible with the webservice requests for fetching the required statements
 */
@Controller
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@Slf4j
public class StatementController {

    public static final String STATEMENT = "statement";
    public static final String STATEMENTS = "statements";
    public static final String ADMIN = "admin";
    public static final String STATEMENT_QUERY = "statementQuery";
    public static final String STATEMENT_INVESTIGATION = "statementinvestigation";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static final String TO_BALANCE = "toBalance";

    @Autowired
    private StatementRepository statementRepository;

    @Autowired
    public StatementController(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    private boolean isAdmin;

    /**
     * Shows all the statements without filtering accountId,date or balance
     *
     * @param model
     * @return
     */
    @GetMapping("/statements")
    public String showStatementList(Model model) {
        model.addAttribute(STATEMENTS, statementRepository.findAll());
        return STATEMENT;
    }

    /**
     * Redirects to initial page called statementinvestigation which is used for filtering and listing statements
     *
     * @param model
     * @return
     */
    @GetMapping("/statementinvestigation")
    public String showStatementInvestigation(Model model) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        if (loggedInUser != null && loggedInUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().
                equals("ROLE_ADMIN"))) {
            isAdmin = true;
            model.addAttribute(ADMIN, true);
        } else {
            isAdmin = false;
            model.addAttribute(ADMIN, false);
        }

        model.addAttribute(STATEMENT_QUERY, new StatementQuery());
        return STATEMENT_INVESTIGATION;
    }

    /**
     * Finds Statement with the filtered inputs from the user and redirects it to same page with the filtered statements
     *
     * @param statementQuery
     * @param result
     * @param model
     * @return
     * @throws ParseException
     */
    @PostMapping("/statementinvestigation")
    public String findStatements(@Valid StatementQuery statementQuery, BindingResult result, Model model) throws ParseException {

        model.addAttribute(ADMIN, isAdmin);
        model.addAttribute(STATEMENT_QUERY, statementQuery);

        if (result.hasErrors()) {
            return STATEMENT_INVESTIGATION;
        }

        if (statementQuery.getFromDate() == null && statementQuery.getToDate() != null) {
            FieldError error = new FieldError(FROM_DATE, FROM_DATE, "Both Date Fields should be filled");
            result.addError(error);
            return STATEMENT_INVESTIGATION;
        }

        if (statementQuery.getFromDate() != null && statementQuery.getToDate() == null) {
            FieldError error = new FieldError(TO_DATE, TO_DATE,
                    "Both Date Fields should be filled ");
            result.addError(error);
            return STATEMENT_INVESTIGATION;
        }

        if (statementQuery.getFromBalance() != null && statementQuery.getToBalance() == null) {
            FieldError error = new FieldError(TO_BALANCE, TO_BALANCE,
                    "Both Balance Fields should be filled ");
            result.addError(error);
            return STATEMENT_INVESTIGATION;
        }

        if (statementQuery.getFromBalance() == null && statementQuery.getToBalance() != null) {
            FieldError error = new FieldError("fromBalance", "fromBalance",
                    "Both Balance Fields should be filled ");
            result.addError(error);
            return STATEMENT_INVESTIGATION;
        }

        // If it's a normal user(not an admin), then the user can only see the 3 months back statements
        if (!isAdmin) {
            statementQuery.setFromDate(new DateTime().minusMonths(3).toDate());
            statementQuery.setToDate(new Date());
        }
        List<Statement> statementList = fetchFilteredStatements(statementQuery.getAccountId(), statementQuery.getFromDate(), statementQuery.getToDate(),
                statementQuery.getFromBalance(), statementQuery.getToBalance());

        model.addAttribute(STATEMENTS, statementList);

        return STATEMENT_INVESTIGATION;
    }

    /**
     * Calculation method which connects to MsAccess DB and fetch the statment list with the required inputs from user
     *
     * @param accountId
     * @param fromDate
     * @param toDate
     * @param fromAmount
     * @param toAmount
     * @return
     * @throws ParseException
     */
    public List<Statement> fetchFilteredStatements(String accountId, Date fromDate, Date toDate, BigDecimal fromAmount,
                                                   BigDecimal toAmount)
            throws ParseException {

        List<Statement> statementList = statementRepository.findStatementsByAccountId(accountId);

        /**
         * If date and amount filters are null, then then there is no requirement to make filter inside statement list
         */
        if (fromDate == null && fromAmount == null) {
            return statementList;
        }

        statementList = getStatements(fromDate, toDate, fromAmount, toAmount, statementList);
        return statementList;
    }

    /**
     * Finds the statements with the filtered inputs
     *
     * @param fromDate
     * @param toDate
     * @param fromAmount
     * @param toAmount
     * @param statementList
     * @return
     * @throws ParseException
     */
    private List<Statement> getStatements(Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount,
                                          List<Statement> statementList) throws ParseException {
        List<Statement> filteredList = new ArrayList<>();

        /**
         * As the requirement to use accountsdb.accdb, the file is not changed. In the DB, datefield and amount areas
         * in String format.So we have to make the filtering inside the class to get required dates between user's
         * choice dates and the amount between user's selected from and to balancxes.
         */
        for (Statement statement : statementList) {

            if (fromDate != null && fromAmount == null) {
                Date date = DateUtil.convertDateStringtoDate(statement.getDateField());
                if (date.before(toDate) && (date.after(fromDate) || date.equals(fromDate))) {
                    filteredList.add(statement);
                }
            } else if (fromAmount != null && fromDate == null) {

                if (new BigDecimal(statement.getAmount()).compareTo(fromAmount) == 1 &&
                        toAmount.compareTo(new BigDecimal(statement.getAmount())) == 1) {
                    filteredList.add(statement);
                }
            } else if (fromAmount != null && fromDate != null) {
                Date date = DateUtil.convertDateStringtoDate(statement.getDateField());
                if (date.before(toDate) && (date.after(fromDate) || date.equals(fromDate)) &&
                        new BigDecimal(statement.getAmount()).compareTo(fromAmount) == 1 &&
                        toAmount.compareTo(new BigDecimal(statement.getAmount())) == 1) {
                    filteredList.add(statement);
                }
            }
        }
        return filteredList;
    }
}
