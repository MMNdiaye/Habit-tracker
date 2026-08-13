package sn.ndiaye.habit_tracker.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ndiaye.habit_tracker.dtos.*;
import sn.ndiaye.habit_tracker.exceptions.AccountNotFoundException;
import sn.ndiaye.habit_tracker.exceptions.AlreadyTakenUsernameException;
import sn.ndiaye.habit_tracker.exceptions.DuplicateHabitNameException;
import sn.ndiaye.habit_tracker.exceptions.HabitNotFoundException;
import sn.ndiaye.habit_tracker.mappers.AccountMapper;
import sn.ndiaye.habit_tracker.mappers.HabitMapper;
import sn.ndiaye.habit_tracker.services.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;
    private AccountMapper accountMapper;
    private HabitMapper habitMapper;

    @PostMapping
    void createAccount(@RequestBody @Valid RegisterAccountDto registerAccountDto) {
        var account = accountMapper.toEntity(registerAccountDto);
        accountService.createAccount(account);
    }

    @GetMapping
    List<AccountDto> getAllAccounts() {
        var accounts = accountService.getAllAccounts();
        return accounts.stream()
                .map(accountMapper::toDto)
                .toList();
    }

    @GetMapping
    List<AccountDto> findAccounts(@RequestParam(name = "username", required = false) String username) {
        var accounts = accountService.findAccounts(username);
        return accounts.stream()
                .map(accountMapper::toDto)
                .toList();
    }

    @PatchMapping("/{accountId}")
    void updateAccount(@PathVariable UUID accountId, @RequestBody @Valid UpdateUserDto updateUserDto) {
        accountService.updateAccount(accountId, updateUserDto.getUsername(), updateUserDto.getPassword());
    }

    @DeleteMapping("/{accountId}")
    ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{accountId}")
    ResponseEntity<AccountDto> getAccount(@PathVariable UUID accountId) {
        var account = accountService.getAccount(accountId);
        return ResponseEntity.ok(accountMapper.toDto(account));
    }

    @PostMapping("/{accountId}/habits")
    void registerHabit(@PathVariable UUID accountId,
                       @RequestBody @Valid RegisterHabitDto registerHabitDto) {
        var habit = habitMapper.toEntity(registerHabitDto);
        accountService.registerHabit(accountId, habit);
    }

    @GetMapping("/{accountId}/habits")
    List<HabitDto> getHabits(@PathVariable UUID accountId) {
        var habits = accountService.getHabits(accountId);
        return habits.stream().map(habit -> habitMapper.toDto(habit))
                .toList();
    }

    @GetMapping("/{accountId}/habits/{habit}")
    HabitDto getHabit(@PathVariable UUID accountId, @PathVariable(name = "habit") String habitName) {
        var habit = accountService.getHabit(accountId, habitName);
        return habitMapper.toDto(habit);
    }

    @PutMapping("/{accountId}/habits/{habit}")
    void renameHabit(@PathVariable UUID accountId,
                     @PathVariable String habit,
                     @RequestBody @Valid RenameHabitRequestDto renameHabitRequestDto) {
        accountService.renameHabit(accountId, habit, renameHabitRequestDto.getNewName());
    }

    @DeleteMapping("/{accountId}/habits/{habit}")
    ResponseEntity<Void> deleteHabit(@PathVariable UUID accountId, @PathVariable String habit) {
        accountService.deleteHabit(accountId, habit);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({AlreadyTakenUsernameException.class, DuplicateHabitNameException.class})
    ResponseEntity<ErrorDto> handleTakenUsername(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler({AccountNotFoundException.class, HabitNotFoundException.class})
    ResponseEntity<Void> handleAccountNotFound() {
        return ResponseEntity.notFound().build();
    }
}
