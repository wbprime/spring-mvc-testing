package me.wbprime.springmvctesting.common.restcontrollers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Class: PingController
 * Date: 2016/04/12 10:23
 *
 * @author Elvis Wang [bo.wang35@renren-inc.com]
 */
@Controller
@RequestMapping("ping")
public class PingController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld() {
        return "{\"ping\": \"ping\"}";
    }
}
