package ru.valerykorzh.springdemo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.dto.AccountDto;
import ru.valerykorzh.springdemo.dto.AccountPostDto;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);

    @Mapping(target = "id", ignore = true)
    Account postDtoToAccount(AccountPostDto accountPostDto);

}
