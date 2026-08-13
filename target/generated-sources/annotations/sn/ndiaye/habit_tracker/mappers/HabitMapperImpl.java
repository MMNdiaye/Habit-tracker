package sn.ndiaye.habit_tracker.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.ndiaye.habit_tracker.dtos.HabitDto;
import sn.ndiaye.habit_tracker.dtos.RegisterHabitDto;
import sn.ndiaye.habit_tracker.entities.Habit;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-13T10:12:09+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class HabitMapperImpl implements HabitMapper {

    @Override
    public Habit toEntity(RegisterHabitDto dto) {
        if ( dto == null ) {
            return null;
        }

        Habit.HabitBuilder habit = Habit.builder();

        habit.name( dto.getName() );

        return habit.build();
    }

    @Override
    public HabitDto toDto(Habit habit) {
        if ( habit == null ) {
            return null;
        }

        HabitDto habitDto = new HabitDto();

        habitDto.setName( habit.getName() );

        return habitDto;
    }
}
