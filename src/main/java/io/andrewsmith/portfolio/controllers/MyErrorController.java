package io.andrewsmith.portfolio.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error2";
    }

    @RequestMapping(value = "/error2", produces = "text/html")
    public String errorPage() {
        return "error.html";
    }
}
