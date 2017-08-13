package io.andrewsmith.portfolio.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class MyErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
