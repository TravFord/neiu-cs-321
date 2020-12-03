package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.data.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/editcourse")
public class EditCourseController {

    private ICourseRepository courseRepo;

    @Autowired
    public EditCourseController(ICourseRepository courseRepo)
    {
        this.courseRepo = courseRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping("/{courseId}")
    public String editCourse(@PathVariable("courseId") long id, Model model)
    {
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(course -> {courses.add(course);});

        Course course = courseRepo.findById(id).get();
        model.addAttribute("course", course);
        return "editcourse";
    }


}
