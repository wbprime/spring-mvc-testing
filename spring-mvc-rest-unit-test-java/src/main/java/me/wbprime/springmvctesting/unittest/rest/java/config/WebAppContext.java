package me.wbprime.springmvctesting.unittest.rest.java.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Class: WebAppContext
 * Date: 2016/04/13 18:33
 *
 * @author Elvis Wang [bo.wang35@renren-inc.com]
 */
@Configuration
@Import({WebMvcContext.class, ServicesContext.class, PersistenceContext.class})
@PropertySource("classpath:db-dev.properties")
public class WebAppContext {
}
