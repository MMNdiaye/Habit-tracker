package sn.ndiaye.habit_tracker.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AlreadyTakenUsernameException extends RuntimeException {
    private String username;

    public AlreadyTakenUsernameException(String username) {
        super("This username is already taken");
        this.username = username;
    }
}
