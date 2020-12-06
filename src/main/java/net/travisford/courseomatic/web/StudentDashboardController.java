package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.data.CourseOfStudyRepository;
import net.travisford.courseomatic.data.CourseProperties;
import net.travisford.courseomatic.security.User;
import net.travisford.courseomatic.data.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/studentdashboard")
public class StudentDashboardController
{
    private ICourseRepository courseRepo;
    private CourseProperties properties;
    private CourseOfStudyRepository studyRepo;

    @Autowired
    public StudentDashboardController(ICourseRepository courseRepo, CourseProperties properties, CourseOfStudyRepository studyRepo)
    {
        this.properties = properties;
        this.courseRepo = courseRepo;
        this.studyRepo = studyRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping
    public String showDashboard()
    {
        return "studentdashboard";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(course -> {courses.add(course);});
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private List<Course> createCourseList(){
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(x -> courses.add(x));

        return courses;
    }
}