package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Entry point to show UI page
 */
@Controller
public class UiController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
