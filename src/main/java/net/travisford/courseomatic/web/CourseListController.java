package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.CourseRepository;
import net.travisford.courseomatic.JdbcCourseRepository;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private CourseRepository courseRepo;

    public CourseListController(CourseRepository courseRepo){
        this.courseRepo = courseRepo;
    }

    @GetMapping
    public String showCourseList()
    {
        return "courselist";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<Course> courses =  createCourseList();

            model.addAttribute("courses", courses);
    }

    private List<Course> createCourseList(){
//        List<Course> courses = Arrays.asList(
//                new Course( (long)1, "300", "CS", "Server Side Web Development", new ArrayList<Course>(), new ArrayList<String>()),
//                new Course( (long)34, "301", "CS", "Databases", new ArrayList<Course>(), new ArrayList<String>()),
//                new Course( (long)745, "100", "CS", "Programming 1", new ArrayList<Course>(), new ArrayList<String>()),
//                new Course( (long)130, "215", "CS", "Client Side Web Development", new ArrayList<Course>(), new ArrayList<String>()),
//                new Course( (long)6, "200", "CS", "Programming 2", new ArrayList<Course>(), new ArrayList<String>())
//        );
//
//        Course.addPrereqToCourse(courses, "CS-200", "CS-100");
//        Course.addPrereqToCourse(courses, "CS-300", "CS-200");
//        Course.addPrereqToCourse(courses, "CS-300", "CS-215");
//        Course.addPrereqToCourse(courses, "CS-301", "CS-200");

        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(x -> courses.add(x));

        return courses;
    }
}
