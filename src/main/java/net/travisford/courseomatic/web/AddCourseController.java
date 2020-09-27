package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.SimpleCourse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/addcourse")
public class AddCourseController {

    @GetMapping
    public String showAddCourse()
    {
        return "addcourse";
    }

    @PostMapping
    public String processAddCourse(@Valid @ModelAttribute("newCourse") SimpleCourse newCourse, Errors errors, Model model)
    {
        if(errors.hasErrors()){
            return "addcourse";
        }


        return "redirect:/addcompleteform";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<SimpleCourse> courses =  createSimpleCourseList();
        SimpleCourse newCourse = new SimpleCourse();
        model.addAttribute("courses", courses);
        model.addAttribute("newCourse", newCourse);

    }

    private List<SimpleCourse> createSimpleCourseList(){
        List<SimpleCourse> courses = Arrays.asList(
                new SimpleCourse( "CS", "300", "Server Side Web Development", new ArrayList<String>()),
                new SimpleCourse( "CS", "301", "Databases", new ArrayList<String>()),
                new SimpleCourse( "CS", "100", "Programming 1", new ArrayList<String>()),
                new SimpleCourse( "CS", "215", "Client Side Web Development", new ArrayList<String>()),
                new SimpleCourse( "CS", "200", "Programming 2", new ArrayList<String>())
        );



        return courses;
    }
}
