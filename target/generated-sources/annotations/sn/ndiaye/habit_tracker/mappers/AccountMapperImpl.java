package sn.ndiaye.habit_tracker.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.ndiaye.habit_tracker.dtos.AccountDto;
import sn.ndiaye.habit_tracker.dtos.HabitDto;
import sn.ndiaye.habit_tracker.dtos.RegisterAccountDto;
import sn.ndiaye.habit_tracker.entities.Account;
import sn.ndiaye.habit_tracker.entities.Habit;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-12T09:50:31+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toEntity(RegisterAccountDto dto) {
        if ( dto == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.username( dto.getUsername() );
        account.password( dto.getPassword() );

        return account.build();
    }

    @Override
    public AccountDto toDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setUsername( account.getUsername() );
        accountDto.setPassword( account.getPassword() );
        accountDto.setHabits( habitListToHabitDtoList( account.getHabits() ) );

        return accountDto;
    }

    protected HabitDto habitToHabitDto(Habit habit) {
        if ( habit == null ) {
            return null;
        }

        HabitDto habitDto = new HabitDto();

        habitDto.setName( habit.getName() );

        return habitDto;
    }

    protected List<HabitDto> habitListToHabitDtoList(List<Habit> list) {
        if ( list == null ) {
            return null;
        }

        List<HabitDto> list1 = new ArrayList<HabitDto>( list.size() );
        for ( Habit habit : list ) {
            list1.add( habitToHabitDto( habit ) );
        }

        return list1;
    }
}
