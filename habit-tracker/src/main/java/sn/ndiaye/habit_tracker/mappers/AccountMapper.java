package sn.ndiaye.habit_tracker.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.ndiaye.habit_tracker.dtos.AccountDto;
import sn.ndiaye.habit_tracker.dtos.RegisterAccountDto;
import sn.ndiaye.habit_tracker.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(RegisterAccountDto dto);
    AccountDto toDto(Account account);
}
