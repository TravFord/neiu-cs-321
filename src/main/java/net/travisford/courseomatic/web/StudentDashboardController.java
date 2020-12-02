package net.travisford.courseomatic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentDashboardController {

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }

}