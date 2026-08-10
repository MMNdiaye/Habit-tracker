package sn.ndiaye.habit_tracker.exceptions;

public class HabitNotFoundException extends RuntimeException{

    public HabitNotFoundException() {
        super("This habit cannot be found");
    }
}
