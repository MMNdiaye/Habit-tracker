package sn.ndiaye.habit_tracker.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ndiaye.habit_tracker.entities.Account;
import sn.ndiaye.habit_tracker.entities.Habit;
import sn.ndiaye.habit_tracker.repositories.AccountRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountService {
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        String username = account.getUsername();
        if (accountRepository.existsByUsername(username))
            throw new IllegalArgumentException("The username " + username + " is already taken");
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No user with this id found"));
    }

    public List<Account> findAccounts(String username) {
        var matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();
        var account = Account.builder().username(username).build();
        var example = Example.of(account, matcher);
        return  accountRepository.findAll(example);
    }

    @Transactional
    public void updateAccount(UUID accountId, String username, String password) {
        var account = getAccount(accountId);
        if (username != null)
            if (accountRepository.existsByUsername(username))
                throw new IllegalArgumentException("The username " + username + " is already taken");
            else
                account.setUsername(username);

        if (password != null)
            account.setPassword(password);
    }

    public void deleteAccount(UUID accountId) {
        accountRepository.delete(getAccount(accountId));
    }

    @Transactional
    public void registerHabit(UUID accountId, Habit habit) {
        var account = getAccount(accountId);
        var registeredHabits = account.getHabits();
        for (var regHabit : registeredHabits)
            if (regHabit.getName().equals(habit.getName()))
                throw new IllegalArgumentException("Habit with this name is already registered");
        account.registerHabit(habit);
    }
}
