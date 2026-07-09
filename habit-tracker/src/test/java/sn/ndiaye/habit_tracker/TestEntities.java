package sn.ndiaye.habit_tracker;

import sn.ndiaye.habit_tracker.entities.Account;
import sn.ndiaye.habit_tracker.entities.Habit;

public class TestEntities {
    public static Account simpleAccount(String username, String password) {
        return Account.builder()
                .username(username)
                .password(password)
                .build();
    }

    public static Habit simpleHabit(String name) {
        return Habit.builder()
                .name(name)
                .build();
    }
}
