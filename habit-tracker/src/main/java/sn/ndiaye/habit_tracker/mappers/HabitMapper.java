package sn.ndiaye.habit_tracker.mappers;

import org.mapstruct.Mapper;
import sn.ndiaye.habit_tracker.dtos.HabitDto;
import sn.ndiaye.habit_tracker.dtos.RegisterHabitDto;
import sn.ndiaye.habit_tracker.entities.Habit;

@Mapper(componentModel = "spring")
public interface HabitMapper {
    Habit toEntity(RegisterHabitDto dto);
    HabitDto toDto(Habit habit);
}
