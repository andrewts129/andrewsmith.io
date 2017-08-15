package io.andrewsmith.portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping(value = "/", produces = "text/html")
    public String homePage() {
        return "index.html";
    }

    @RequestMapping(value = "/biggest-one-hit-wonders", produces = "text/html")
    public String oneHitWondersPage() {
        return "one-hit-wonders.html";
    }
}
