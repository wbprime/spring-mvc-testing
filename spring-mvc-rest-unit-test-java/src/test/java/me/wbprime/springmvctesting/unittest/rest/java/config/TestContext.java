package me.wbprime.springmvctesting.unittest.rest.java.config;


import me.wbprime.springmvctesting.common.services.TodoService;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Class: TestContext
 * Date: 2016/04/10 12:33
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
public class TestContext {
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
