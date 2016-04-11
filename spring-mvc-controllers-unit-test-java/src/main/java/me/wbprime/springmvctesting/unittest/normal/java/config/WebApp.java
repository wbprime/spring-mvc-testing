package me.wbprime.springmvctesting.unittest.normal.java.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class: WebApp
 * Date: 2016/04/11 23:01
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
public final class WebApp extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {
            WebAppContext.class,
            ServicesContext.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }
}
