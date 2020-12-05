package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.data.CourseProperties;
import net.travisford.courseomatic.security.User;
import net.travisford.courseomatic.data.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courselist")
public class CourseListController {

    private ICourseRepository courseRepo;
    private CourseProperties properties;

    @Autowired
    public CourseListController(ICourseRepository courseRepo, CourseProperties properties)
    {
       this.properties = properties;

        this.courseRepo = courseRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping
    public String showCourseList()
    {
        return "courselist";
    }

    @DeleteMapping("/delete/courseId")
    public String processEditCourse(@PathVariable("courseId") long courseId, @Valid @ModelAttribute("course") Course course, Errors errors)
    {
        if(errors.hasErrors())
        {
            return "courselist";
        }

        Course deletedCourse = courseRepo.findById(courseId).get();
        deletedCourse.deleteFromDb();

        return "redirect:/courselist";
    }
    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(course -> {courses.add(course);});

        List<Course> limitedCourses = new ArrayList<Course>();
        for(int i = 0; i < properties.getCourseDisplayLimit() && i < courses.size() ; i++ )
        {
            limitedCourses.add(courses.get(i));
        }
        model.addAttribute("courses", limitedCourses);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("selectedCourse", new Course()); //Aaaaagh, I don't know how else to track which class is selected

    }

    private List<Course> createCourseList(){
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(x -> courses.add(x));

        return courses;
    }
}
