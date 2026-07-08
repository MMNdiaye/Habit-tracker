package sn.ndiaye.habit_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sn.ndiaye.habit_tracker.entities.Account;
import sn.ndiaye.habit_tracker.services.AccountService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
class AccountTest {
    @Autowired
    private AccountService service;

    @Test
    @Transactional
    void account_can_be_created() {
        var account = makeAccount("New", "password");
        account = service.createAccount(account);
        assertThat(account.getId()).isNotNull();
    }

    @Test
    @Transactional
    void new_account_cannot_register_taken_username() {
        var account1 = makeAccount("New", "password");
        var account2 = makeAccount("New", "password");
        service.createAccount(account1);
        assertThrows(IllegalArgumentException.class,
                () -> service.createAccount(account2));
    }



    private Account makeAccount(String username, String password) {
        return Account.builder()
                .username("New")
                .password("password")
                .build();
    }

}
