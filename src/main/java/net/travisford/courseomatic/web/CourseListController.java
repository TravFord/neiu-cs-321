package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.security.User;
import net.travisford.courseomatic.data.ICourseRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/courselist")
public class CourseListController {

    private ICourseRepository courseRepo;

    public CourseListController(ICourseRepository courseRepo)
    {
        this.courseRepo = courseRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping
    public String showCourseList()
    {
        return "courselist";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(course -> {courses.add(course);});

        model.addAttribute("courses", courses);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    private List<Course> createCourseList(){
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(x -> courses.add(x));

        return courses;
    }
}
