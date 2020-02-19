package ru.valerykorzh.springdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.valerykorzh.springdemo.domain.Account;
import ru.valerykorzh.springdemo.domain.Question;


import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static ru.valerykorzh.springdemo.controller.ControllerConstants.QUESTIONS_PATH;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionControllerTest extends AbstractControllerTest {

    private static final long NON_EXISTED_QUESTION_ID = 1000L;

    private static final String CONTENT_TYPE = ContentType.HTML.withCharset(StandardCharsets.UTF_8);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        auditingHandler = getAuditingHandler();
        auditorAware = mock(AuditorAware.class);
        when(auditorAware.getCurrentAuditor())
                .thenReturn(Optional.of(createAccount(DEFAULT_USER_NAME, DEFAULT_USER_EMAIL)));

        Account account = createAccountWithId(EXISTED_USER_ID, DEFAULT_USER_NAME, DEFAULT_USER_EMAIL);
        Question question = createQuestionWithId(EXISTED_QUESTION_ID, account);
        auditingHandler.markCreated(question);
        when(questionService.findById(EXISTED_QUESTION_ID)).thenReturn(Optional.of(question));
    }

    @Test
    @WithMockUser
    void getQuestionById() throws Exception {
        mvc.perform(get(QUESTIONS_PATH + "/" + EXISTED_QUESTION_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(containsString("test question")))
                .andExpect(content().string(containsString("text for test question")));
    }

    @Test
    @WithMockUser
    void getQuestionByIdNotFound() throws Exception {
        when(questionService.findById(NON_EXISTED_QUESTION_ID)).thenReturn(Optional.empty());

        mvc.perform(get(QUESTIONS_PATH + "/" + NON_EXISTED_QUESTION_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(
                        containsString(String.format("Question with id %d not found", NON_EXISTED_QUESTION_ID))));
    }

    @Test
    @WithMockUser
    void getCreationForm() throws Exception {
        mvc.perform(get(QUESTIONS_PATH + "/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));
    }

    @Test
    void deleteQuestion() throws Exception {
        doNothing().when(questionService).deleteById(any());
        mvc.perform(delete(QUESTIONS_PATH + "/" + EXISTED_QUESTION_ID).with(csrf()))
                .andExpect(status().isFound());
    }
}