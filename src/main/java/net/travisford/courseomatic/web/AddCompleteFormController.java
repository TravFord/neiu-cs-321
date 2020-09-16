package net.travisford.courseomatic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/addcompleteform")
public class AddCompleteFormController {

    @GetMapping()
    public String addComplete(){
        return "addcompleteform";
    }

}
