package me.wbprime.springmvctesting.unittest.normal.java.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Class: ServicesContext
 * Date: 2016/04/11 23:14
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
@Configuration
@ComponentScan(basePackages = {"me.wbprime.springmvctesting.common.services" })
public class ServicesContext {
    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
}
