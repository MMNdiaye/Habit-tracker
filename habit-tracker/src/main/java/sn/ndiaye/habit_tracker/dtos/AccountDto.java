package sn.ndiaye.habit_tracker.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AccountDto {
    private String username;
    private String password;
    private List<HabitDto> habits;
}
