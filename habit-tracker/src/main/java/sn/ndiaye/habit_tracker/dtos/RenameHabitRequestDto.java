package sn.ndiaye.habit_tracker.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RenameHabitRequestDto {
    @NotBlank
    private String newName;
}
