package nagarro.jpa.controller;

import nagarro.StatementInvestigationApplication;
import nagarro.jpa.repository.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class AccountControllerTest {

    private static AccountController accountController;
    private static AccountRepository mockedAccountRepository;
    private static Model mockedModel;

    @BeforeAll
    public static void setUpUserControllerInstance() {
        mockedAccountRepository = mock(AccountRepository.class);
        mockedModel = mock(Model.class);
        accountController = new AccountController(mockedAccountRepository);
    }

    @Test
    public void whenCalledAccount_thenCorrect() {
        assertThat(accountController.showAccountList(mockedModel)).isEqualTo("account");
    }
}
