package nagarro.jpa.controller;


import nagarro.jpa.entity.Statement;
import nagarro.jpa.entity.StatementQuery;
import nagarro.jpa.repository.StatementRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class StatementControllerTest {

    private static StatementController statementController;
    private static StatementRepository mockedStatementRepository;
    private static Model mockedModel;
    private static BindingResult mockedBindingResult;

    @BeforeAll
    public static void setUpUserControllerInstance() {
        mockedStatementRepository = mock(StatementRepository.class);
        mockedModel = mock(Model.class);
        mockedBindingResult = mock(BindingResult.class);
        statementController = new StatementController(mockedStatementRepository);
    }

    @Test
    public void whenCalledStatement_thenCorrect() {
        assertThat(statementController.showStatementList(mockedModel)).isEqualTo("statement");
    }

    @Test
    public void whenCalledStatementInvestigation_thenCorrect() {
        assertThat(statementController.showStatementInvestigation(mockedModel)).isEqualTo("statementinvestigation");
    }

    @Test
    public void whenCalledFindStatements_thenCorrect() throws ParseException {

        StatementQuery statementQuery = new StatementQuery("123",new Date(),new Date(),new BigDecimal("100"),new BigDecimal("100000"));

        assertThat(statementController.findStatements(statementQuery, mockedBindingResult, mockedModel)).isEqualTo("statementinvestigation");
    }

    @Test
    public void whenCalledFethFilteredStatements_thenCorrect() throws ParseException {

        List<Statement> statementList = statementController.fetchFilteredStatements("3.0",null,null,
               null,null);
        assertThat(statementList).isNotNull();

        statementList = statementController.fetchFilteredStatements("3.0",null,null,
                new BigDecimal("1.0"),new BigDecimal("1000.0"));
        assertThat(statementList).isNotNull();
    }




}
