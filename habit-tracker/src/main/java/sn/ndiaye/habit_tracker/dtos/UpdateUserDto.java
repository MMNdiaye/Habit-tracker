package sn.ndiaye.habit_tracker.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import sn.ndiaye.habit_tracker.utils.Password;

@Data
public class UpdateUserDto {
    @NotBlank
    private String username;

    @NotBlank
    @Password
    private String password;
}
