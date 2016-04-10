package me.wbprime.controllers;


import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import me.wbprime.springmvctesting.unittest.normal.java.services.TodoService;

/**
 * Class: TestContent
 * Date: 2016/04/10 12:33
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
public class TestContent {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    public TodoService todoService() {
        return Mockito.mock(TodoService.class);
    }
}
