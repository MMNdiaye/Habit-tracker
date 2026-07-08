package sn.ndiaye.habit_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sn.ndiaye.habit_tracker.entities.Account;
import sn.ndiaye.habit_tracker.services.AccountService;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class AccountServiceTest {
    @Autowired
    private AccountService service;

    @Test
    void account_can_be_created() {
        var account = makeAccount("New", "password");
        account = service.createAccount(account);
        assertThat(account.getId()).isNotNull();
    }

    @Test
    void new_account_cannot_register_taken_username() {
        var account1 = makeAccount("New", "password");
        var account2 = makeAccount("New", "password");
        service.createAccount(account1);
        assertThrows(IllegalArgumentException.class,
                () -> service.createAccount(account2));
    }

    @Test
    void only_existing_id_can_find_accounts() {
        var account = makeAccount("New", "Password");
        service.createAccount(account);
        var id = account.getId();
        assertThat(service.getAccount(id)).isEqualTo(account);
        assertThrows(NoSuchElementException.class,
                () -> service.getAccount(UUID.randomUUID()));
    }

    @Test
    void a_list_of_filtered_accounts_can_be_obtained() {
        var account1 = makeAccount("New", "Password");
        var account2 = makeAccount("Old", "Password");
        var account3 = makeAccount("Dead", "Password");
        service.createAccount(account1);
        service.createAccount(account2);
        service.createAccount(account3);

        var accountsContainingE = service.findAccounts("e");
        var accountsContainingD = service.findAccounts("D");
        assertThat(accountsContainingE).contains(account1, account3);
        assertThat(accountsContainingE).doesNotContain(account2);
        assertThat(accountsContainingD).contains(account2, account3);
        assertThat(accountsContainingD).doesNotContain(account1);
    }

    @Test
    void account_cannot_change_username_to_an_existing_one() {
        var account = makeAccount("New", "Password");
        service.createAccount(account);
        assertThrows(IllegalArgumentException.class, () ->
                service.updateAccount(account, "New", "Password"));
    }

    @Test
    void account_infos_can_be_modified() {
        var account = makeAccount("New", "Password");
        service.createAccount(account);
        service.updateAccount(account, "Old", "MoreSecure");
        assertThat(account.getUsername()).isEqualTo("Old");
        assertThat(account.getPassword()).isEqualTo("MoreSecure");
    }


    private Account makeAccount(String username, String password) {
        return Account.builder()
                .username(username)
                .password(password)
                .build();
    }

}
