package sn.ndiaye.habit_tracker.exceptions;

import lombok.Getter;

@Getter
public class DuplicateHabitNameException extends RuntimeException {
    private String habit;

    public DuplicateHabitNameException(String habit) {
        super("Account has already registered habit with this name");
        this.habit = habit;
    }
}
