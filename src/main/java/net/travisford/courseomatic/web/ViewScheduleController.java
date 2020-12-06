package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.CourseOfStudy;
import net.travisford.courseomatic.data.CourseOfStudyRepository;
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
@RequestMapping("/viewschedule")
public class ViewScheduleController {

    private ICourseRepository courseRepo;
    private CourseProperties properties;
    private CourseOfStudyRepository studyRepo;
    @Autowired
    public ViewScheduleController(ICourseRepository courseRepo, CourseProperties properties, CourseOfStudyRepository studyRepo)
    {
        this.properties = properties;
        this.studyRepo = studyRepo;
        this.courseRepo = courseRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping
    public String showCourseList()
    {
        return "viewschedule";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CourseOfStudy cStudy = studyRepo.findAll().stream().filter(x->x.getUser().getId() == user.getId()).findFirst().get();
        ArrayList<Course> courses = new ArrayList<>(cStudy.getCourses());
        model.addAttribute("courses", courses);
        model.addAttribute(user);
    }

    private List<Course> createCourseList(){
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(x -> courses.add(x));

        return courses;
    }
}
