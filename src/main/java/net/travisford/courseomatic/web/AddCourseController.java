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
        List<Course> courses =  createCourseList();
        SimpleCourse newCourse = new SimpleCourse();
        model.addAttribute("courses", courses);
        model.addAttribute("newCourse", newCourse);

    }

    private List<Course> createCourseList(){
        List<Course> courses = Arrays.asList(
                new Course( "1", "300", "CS", "Server Side Web Development", new ArrayList<Course>(), new ArrayList<String>()),
                new Course( "34", "301", "CS", "Databases", new ArrayList<Course>(), new ArrayList<String>()),
                new Course( "745", "100", "CS", "Programming 1", new ArrayList<Course>(), new ArrayList<String>()),
                new Course( "130", "215", "CS", "Client Side Web Development", new ArrayList<Course>(), new ArrayList<String>()),
                new Course( "6", "200", "CS", "Programming 2", new ArrayList<Course>(), new ArrayList<String>())
        );

        Course.addPrereqToCourse(courses, "CS-200", "CS-100");
        Course.addPrereqToCourse(courses, "CS-300", "CS-200");
        Course.addPrereqToCourse(courses, "CS-300", "CS-215");
        Course.addPrereqToCourse(courses, "CS-301", "CS-200");

        return courses;
    }
}
