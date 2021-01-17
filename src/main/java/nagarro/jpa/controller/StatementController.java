package nagarro.jpa.controller;

import nagarro.jpa.entity.Statement;
import nagarro.jpa.entity.StatementQuery;
import nagarro.jpa.repository.StatementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author alperkopuz
 */
@Controller
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@Slf4j
public class StatementController {

    @Autowired
    private StatementRepository statementRepository;

    private boolean isAdmin;

    @GetMapping("/statements")
    public String showStatementList(Model model) {
        model.addAttribute("statements", statementRepository.findAll());
        return "statement";
    }

    @GetMapping("/statementinvestigation")
    public String showStatementInvestigation(Model model) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication(); //TODO you can move it to another place later

        if (loggedInUser != null && loggedInUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            isAdmin = true;
            model.addAttribute("admin", true);
        } else {
            isAdmin = false;
            model.addAttribute("admin", false);
        }

        model.addAttribute("statementQuery", new StatementQuery());
        return "statementInvestigation";
    }

    @PostMapping("/statementinvestigation")
    public String findStatements(@Valid StatementQuery statementQuery, BindingResult result, Model model) throws ParseException {

        model.addAttribute("admin", isAdmin);
        model.addAttribute("statementQuery", statementQuery);

        if (result.hasErrors()) {
            return "statementInvestigation";
        }

        if (statementQuery.getFromDate() == null && statementQuery.getToDate() != null) {
            FieldError error = new FieldError("fromDate", "fromDate",
                    "Both Date Fields should be filled ");
            result.addError(error);
            return "statementInvestigation"; //TODO static constant olarak yazmaya calis boyle degerleri
        }

        if (statementQuery.getFromDate() != null && statementQuery.getToDate() == null) {
            FieldError error = new FieldError("toDate", "toDate",
                    "Both Date Fields should be filled ");
            result.addError(error);
            return "statementInvestigation";
        }

        if (statementQuery.getFromBalance() != null && statementQuery.getToBalance() == null) {
            FieldError error = new FieldError("toBalance", "toBalance",
                    "Both Balance Fields should be filled ");
            result.addError(error);
            return "statementInvestigation";
        }

        if (statementQuery.getFromBalance() == null && statementQuery.getToBalance() != null) {
            FieldError error = new FieldError("fromBalance", "fromBalance",
                    "Both Balance Fields should be filled ");
            result.addError(error);
            return "statementInvestigation";
        }

        List<Statement> statementList = findStatements(statementQuery.getAccountId(), statementQuery.getFromDate(), statementQuery.getToDate(),
                statementQuery.getFromBalance(), statementQuery.getToBalance());

        model.addAttribute("statements", statementList);

        return "statementInvestigation";
    }

    public List<Statement> findStatements(String accountId, Date fromDate, Date toDate, BigDecimal fromAmount, BigDecimal toAmount)
            throws ParseException { //TODO exception please


        List<Statement> statementList = statementRepository.findStatementsByAccountId(accountId);
        if (fromDate == null && fromAmount == null) {
            return statementList;
        }

        List<Statement> filteredList = new ArrayList<>();

        for (Statement statement : statementList) { //TODO tell the reason why you are doing it like that

            if (fromDate != null && fromAmount == null) {
                DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy"); //TODO move me to util method
                Date date = formatter.parse(statement.getDateField());
                if (date.before(toDate) && (date.after(fromDate) || date.equals(fromDate))) { //TODO describe it
                    filteredList.add(statement);  //TODO please think about all exceptions that might occure there
                    //TOO please think about if user writes wrong dates try to throw them at frontend !!
                }
            } else if (fromAmount != null && fromDate == null) {

                if (new BigDecimal(statement.getAmount()).compareTo(fromAmount) == 1 &&
                        toAmount.compareTo(new BigDecimal(statement.getAmount())) == 1) {
                    filteredList.add(statement);  //TODO please think about all exceptions that might occure there
                }
            } else if (fromAmount != null && fromDate != null) {
                DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy"); //TODO move me to util method
                Date date = formatter.parse(statement.getDateField());
                if (date.before(toDate) && (date.after(fromDate) || date.equals(fromDate)) &&
                        new BigDecimal(statement.getAmount()).compareTo(fromAmount) == 1 &&
                        toAmount.compareTo(new BigDecimal(statement.getAmount())) == 1) { //TODO describe it
                    filteredList.add(statement);  //TODO please think about all exceptions that might occure there
                }
            }
        }
        return filteredList;
    }
}
