package sn.ndiaye.habit_tracker.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ndiaye.habit_tracker.entities.Account;
import sn.ndiaye.habit_tracker.entities.Habit;
import sn.ndiaye.habit_tracker.exceptions.AccountNotFoundException;
import sn.ndiaye.habit_tracker.exceptions.AlreadyTakenUsernameException;
import sn.ndiaye.habit_tracker.exceptions.DuplicateHabitNameException;
import sn.ndiaye.habit_tracker.exceptions.HabitNotFoundException;
import sn.ndiaye.habit_tracker.repositories.AccountRepository;
import sn.ndiaye.habit_tracker.repositories.HabitRepository;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountService {
    private AccountRepository accountRepository;
    private HabitRepository habitRepository;

    public Account createAccount(Account account) {
        String username = account.getUsername();
        if (accountRepository.existsByUsername(username))
            throw new AlreadyTakenUsernameException(username);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(UUID id)  {
        return accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }

    public List<Account> findAccounts(String username) {
        var matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();
        var account = Account.builder().username(username).build();
        var example = Example.of(account, matcher);
        return accountRepository.findAll(example);
    }

    @Transactional
    public void updateAccount(UUID accountId, String username, String password) {
        var account = getAccount(accountId);
        if (username != null)
            if (accountRepository.existsByUsername(username))
                throw new AlreadyTakenUsernameException(username);
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
        var isHabitNameFree = account.getHabits()
                .stream()
                .map(Habit::getName)
                .noneMatch(habName -> habName.equals(habit.getName()));

        if (!isHabitNameFree)
            throw new DuplicateHabitNameException(habit.getName());
        account.registerHabit(habit);
    }

    public List<Habit> getHabits(UUID accountId) {
        var account = getAccount(accountId);
        return List.copyOf(account.getHabits());
    }

    public Habit getHabit(UUID accountId, String habitName) {
        return habitRepository.findByOwnerIdAndName(accountId, habitName)
                .orElseThrow(HabitNotFoundException::new);
    }

    @Transactional
    public void renameHabit(UUID accountId, String habitName, String newName) {
        var habit = habitRepository.findByOwnerIdAndName(accountId, habitName)
                .orElseThrow(HabitNotFoundException::new);

        var newHabit = habitRepository.findByOwnerIdAndName(accountId, newName)
                .orElse(null);
        if (newHabit != null)
            throw new DuplicateHabitNameException(newName);

        habit.setName(newName);
    }

    @Transactional
    public void deleteHabit(UUID accountId, String habitName){
        var account = getAccount(accountId);
        var registeredHabits = account.getHabits();
        for (var regHabit : registeredHabits) {
            if (regHabit.getName().equals(habitName)) {
                account.deleteHabit(regHabit);
                return;
            }
        }
        throw new HabitNotFoundException();
    }
}
