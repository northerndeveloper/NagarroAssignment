package comp.project.backend.jpa.controller;

import comp.project.backend.jpa.persistence.Account;
import comp.project.backend.jpa.persistence.Statement;
import comp.project.backend.jpa.persistence.StatementRepository;
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
public class StatementController {

    @Autowired
    private StatementRepository statementRepository;

    @GetMapping(value = "/statements", headers = "Accept=application/json")
    public List<Statement> getStatements() {
        return statementRepository.findAll();
    }


}
