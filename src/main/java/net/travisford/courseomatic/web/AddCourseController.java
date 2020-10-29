package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.data.ICourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/addcourse")
public class AddCourseController {

    private ICourseRepository courseRepo;

    public AddCourseController(ICourseRepository courseRepo)
    {
        this.courseRepo = courseRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping
    public String showAddCourse()
    {
        return "addcourse";
    }

    @PostMapping
    public String processAddCourse(@Valid @ModelAttribute("newCourse") Course newCourse, Errors errors, Model model)
    {
        if(errors.hasErrors()){
            return "/addcourse";
        }
        newCourse.saveToDb();
        return "redirect:/addcompleteform";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<Course> courses =  createCourseList();
        Course newCourse = new Course(courseRepo);
        model.addAttribute("courses", courses);
        model.addAttribute("newCourse", newCourse);

    }

    private List<Course> createCourseList(){

        ArrayList<Course> output = new ArrayList<>();
        courseRepo.findAll().forEach(x -> output.add(x));
        return output;
    }
}