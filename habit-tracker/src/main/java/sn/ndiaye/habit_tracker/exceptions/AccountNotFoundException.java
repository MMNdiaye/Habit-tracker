package sn.ndiaye.habit_tracker.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super("This account cannot be found");
    }
}
