package me.wbprime.springmvctesting.unittest.normal.java.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class: ErrorCOntroller
 * Date: 2016/04/10 20:05
 *
 * @author Elvis Wang [mail@wbprime.me]
 */
@Controller
@RequestMapping("error")
public class ErrorCOntroller {
    private static Logger logger = LoggerFactory.getLogger(ErrorCOntroller.class);

    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String show404Page() {
        return "/error/404";
    }

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String showInternalServerErrorPage() {
        return "/error/error";
    }
}
