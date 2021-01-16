package comp.project.backend.jpa.controller;

import comp.project.backend.jpa.persistence.Statement;
import comp.project.backend.jpa.persistence.StatementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
@RestController
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@Slf4j
public class StatementController {

    @Autowired
    private StatementRepository statementRepository;


    @GetMapping(value = "/statements", headers = "Accept=application/json")
    public List<Statement> getStatements() {
        return statementRepository.findAll();
    }

    @GetMapping(value = "/findStatements", headers = "Accept=application/json")
    public List<Statement> findStatements(@RequestParam(name = "accountId") String accountId,
                                          @RequestParam(name = "fromDate", required=false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date fromDate,
                                          @RequestParam(name = "toDate", required=false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date toDate,
                                          @RequestParam(name = "fromAmount", required=false) BigDecimal fromAmount,
                                          @RequestParam(name = "toAmount", required=false) BigDecimal toAmount) throws ParseException { //TODO exception please

        //TODO accountID boş ise hemen exception fırlat hatta bunu ekrana yap

        List<Statement> statementList = statementRepository.findStatementsByAccountId(accountId);
        if (fromDate == null && toDate == null && fromAmount == null && toAmount == null) {  //TODO null kısımları bi test et
            return statementList;
        }

        List<Statement> filteredList = new ArrayList<>();

        //TODO fromDate dolu ama toDate boşs ise error fırlat ikisinin de dolu olması lazım olmadı bunları ön yüzde hallet

        //TODO tarihler doğru girilmemişse hemen hata fırlat ikisinin de dolu olması lazım  olmadı bunları ön yüzde hallet

        //TODO fromBalance to Balance doğru girilmişmi falan bu kontrolleri yap


        if (fromDate != null || fromAmount != null) {
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


        }

        return filteredList;
    }

}
