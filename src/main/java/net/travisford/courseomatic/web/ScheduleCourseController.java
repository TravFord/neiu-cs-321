package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.CourseOfStudy;
import net.travisford.courseomatic.data.CourseOfStudyRepository;
import net.travisford.courseomatic.data.CourseProperties;
import net.travisford.courseomatic.data.ICourseRepository;
import net.travisford.courseomatic.security.User;
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
public class ScheduleCourseController {

    private ICourseRepository courseRepo;
    private CourseOfStudyRepository studyRepo;
    private CourseProperties properties;


    @Autowired
    public ScheduleCourseController(ICourseRepository courseRepo, CourseProperties properties, CourseOfStudyRepository studyRepo)
    {
        this.studyRepo = studyRepo;
        this.courseRepo = courseRepo;
    }

    @GetMapping("schedulecourse/{courseId}")
    public String showScheduleCourse(@PathVariable("courseId") long id, Model model)
    {

        return "schedulecourse";


    }

    @PostMapping("schedulecourse/{courseId}")
    public String scheduleCourse(@PathVariable("courseId") long courseId, @Valid @ModelAttribute("course") Course course, Errors errors)
    {
            if(errors.hasErrors())
            {
                return "courselist";
            }
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Course scheduled = courseRepo.findById(courseId).get();
            CourseOfStudy cos = studyRepo.findAll().stream().filter(x->x.getUser().getId() == user.getId()).findAny().get();
            cos.upsertCourse(scheduled,0);

            return "redirect:/courselist";
    }



    @ModelAttribute
    private void addAttributes(Model model)
    {
        List<Course> courses =  createCourseList();
        model.addAttribute("courses", courses);

    }

    private List<Course> createCourseList(){

        ArrayList<Course> output = new ArrayList<>();
        courseRepo.findAll().forEach(x -> output.add(x));
        return output;
    }
}

