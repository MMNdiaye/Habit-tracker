package sn.ndiaye.habit_tracker.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ndiaye.habit_tracker.dtos.AccountDto;
import sn.ndiaye.habit_tracker.dtos.ErrorDto;
import sn.ndiaye.habit_tracker.dtos.RegisterAccountDto;
import sn.ndiaye.habit_tracker.dtos.RegisterHabitDto;
import sn.ndiaye.habit_tracker.exceptions.AccountNotFoundException;
import sn.ndiaye.habit_tracker.exceptions.AlreadyTakenUsernameException;
import sn.ndiaye.habit_tracker.exceptions.DuplicateHabitNameException;
import sn.ndiaye.habit_tracker.mappers.AccountMapper;
import sn.ndiaye.habit_tracker.mappers.HabitMapper;
import sn.ndiaye.habit_tracker.services.AccountService;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;
    private AccountMapper accountMapper;
    private HabitMapper habitMapper;

    @PostMapping
    void createAccount(@RequestBody RegisterAccountDto registerAccountDto) {
        var account = accountMapper.toEntity(registerAccountDto);
        accountService.createAccount(account);
    }

    @GetMapping("/{accountId}")
    ResponseEntity<AccountDto> getAccount(@PathVariable UUID accountId) {
        var account = accountService.getAccount(accountId);
        return ResponseEntity.ok(accountMapper.toDto(account));
    }

    @PostMapping("/{accountId}/habits")
    void registerHabit(@PathVariable UUID accountId,
                       @RequestBody RegisterHabitDto registerHabitDto) {
        var habit = habitMapper.toEntity(registerHabitDto);
        accountService.registerHabit(accountId, habit);
    }

    @ExceptionHandler({AlreadyTakenUsernameException.class, DuplicateHabitNameException.class})
    ResponseEntity<ErrorDto> handleTakenUsername(RuntimeException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorDto(exception.getMessage()));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    ResponseEntity<?> handleAccountNotFound() {
        return ResponseEntity.notFound().build();
    }
}
