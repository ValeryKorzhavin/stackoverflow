package ru.valerykorzh.springdemo.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.valerykorzh.springdemo.controller.AbstractControllerTest;
import ru.valerykorzh.springdemo.domain.Account;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static ru.valerykorzh.springdemo.controller.ControllerConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    private static long EXISTED_USER_ID = 1L;

    private static long NON_EXISTED_USER_ID = 1000L;

    private static String DEFAULT_USER_EMAIL = "test@gmail.com";

    private final String CONTENT_TYPE = ContentType.HTML.withCharset(StandardCharsets.UTF_8);

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();

        when(accountService.findByEmail(any()))
                .thenReturn(Optional.of(createAccount(DEFAULT_USER_NAME, DEFAULT_USER_EMAIL)));
        when(accountService.findById(EXISTED_USER_ID))
                .thenReturn(Optional.of(createAccountWithId(EXISTED_USER_ID, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL)));
        when(accountService.findByEmail(any()))
                .thenReturn(Optional.of(createAccount(DEFAULT_USER_NAME, DEFAULT_USER_EMAIL)));
        when(accountService.save(any()))
                .thenReturn(createAccountWithId(EXISTED_USER_ID, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL));
    }

    @AfterEach
    public void tearDown() {
    }

    @WithMockUser
    @Test
    public void getAccounts() throws Exception {
        given(accountService.findAll(any(Pageable.class))).willAnswer(invocationOnMock -> {
            Pageable pageable = invocationOnMock.getArgument(0);
            List<Account> users = Arrays.asList(
                createAccountWithId(EXISTED_USER_ID, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL),
                createAccountWithId(EXISTED_USER_ID + 1, DEFAULT_USER_NAME + 1, "testAccounts1@mail.com"));
            return new PageImpl<Account>(users, pageable, users.size());
        });
    }

    @WithMockUser
    @Test
    public void getAccountByIdNotFound() throws Exception {
        when(accountService.findById(NON_EXISTED_USER_ID)).thenReturn(Optional.empty());

        mvc.perform(get(ACCOUNTS_PATH + "/" + NON_EXISTED_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(containsString(
                        String.format("Account with id %d not found", NON_EXISTED_USER_ID)))
                );
    }

    @WithMockUser
    @Test
    public void getAccountById() throws Exception {
        mvc.perform(get(ACCOUNTS_PATH + "/" + EXISTED_USER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(CONTENT_TYPE))
            .andExpect(content().string(containsString(DEFAULT_USER_NAME)));
    }

    @WithMockUser
    @Test
    public void getCreationForm() throws Exception {
        mvc.perform(get(LOGIN_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));
    }

    @WithMockUser
    @Test
    public void postAccount() throws Exception {
        mvc.perform(post(REGISTRATION_PATH)
            .param("email", DEFAULT_USER_EMAIL)
            .param("name", DEFAULT_USER_NAME)
            .param("password", DEFAULT_USER_PASSWORD)
            .param("passwordConfirm", DEFAULT_USER_PASSWORD)
            .with(csrf())
        ).andExpect(status().isOk()); // isFound

        Account actualAccount = accountService.findByEmail(DEFAULT_USER_EMAIL).orElse(null);
        assertNotNull(actualAccount);
        assertEquals(actualAccount.getName(), DEFAULT_USER_NAME);
        assertEquals(actualAccount.getEmail(), DEFAULT_USER_EMAIL);
    }

    @WithMockUser
    @Test
    public void getEditForm() throws Exception {
        mvc.perform(get(String.format("%s/edit/%d", ACCOUNTS_PATH, EXISTED_USER_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content()
                        .string(containsString(String.format("name=\"name\" value=\"%s\"", DEFAULT_USER_NAME))))
                .andExpect(content()
                        .string(containsString(String.format("name=\"email\" value=\"%s\"", DEFAULT_USER_EMAIL))));
    }

    @WithMockUser
    @Test
    public void putAccount() throws Exception {

    }

    @WithMockUser
    @Test
    public void deleteAccount() throws Exception {
        doNothing().when(accountService).deleteById(any());
        mvc.perform(delete(ACCOUNTS_PATH + "/" + EXISTED_USER_ID).with(csrf()))
            .andExpect(status().isFound());
    }

}