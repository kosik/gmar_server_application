package by.gmar.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author s.kosik
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    
    @RequestMapping(value = "/robots.txt", method = RequestMethod.GET)
    public String robots() {
        return "forward:/resources/robots.txt";
    }

    @RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET)
    public String sitemap() {
        return "forward:/resources/sitemap.xml";
    }

    @RequestMapping(value = "/18CC7BB4D1613CF80DE9CA1E3DF97AF5.txt", method = RequestMethod.GET)
    public String sslsCertVerification() {
        return "forward:/resources/18CC7BB4D1613CF80DE9CA1E3DF97AF5.txt";
    }
    
}
