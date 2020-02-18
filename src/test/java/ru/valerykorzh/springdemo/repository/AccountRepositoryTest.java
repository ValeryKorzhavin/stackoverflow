package ru.valerykorzh.springdemo.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.valerykorzh.springdemo.domain.Account;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findByName() {
        Account user = Account.builder()
                .name("john")
                .email("john@gmail.com")
                .password("password")
                .build();
        entityManager.persistAndFlush(user);
        List<Account> found = accountRepository.findByName("john");
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getName()).isEqualTo(user.getName());

    }

}