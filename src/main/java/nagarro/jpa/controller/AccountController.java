package nagarro.jpa.controller;

import nagarro.jpa.entity.Account;
import nagarro.jpa.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author alperkopuz
 */
@RestController
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@Slf4j
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping(value = "/accounts", headers = "Accept=application/json")
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }


}
