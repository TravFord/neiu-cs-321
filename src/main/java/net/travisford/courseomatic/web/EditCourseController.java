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
public class EditCourseController {

    private ICourseRepository courseRepo;

    @Autowired
    public EditCourseController(ICourseRepository courseRepo)
    {
        this.courseRepo = courseRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping("editcourse/{courseId}")
    public String showEditCourse(@PathVariable("courseId") long id, Model model)
    {
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(course -> {courses.add(course);});

        Course course = courseRepo.findById(id).get();
        model.addAttribute("course", course);
        addAttributes(model);
        return "editcourse";
    }

    @PostMapping("editcourse/{courseId}")
    public String editCourse(@PathVariable("courseId") long id, @Valid @ModelAttribute("course") Course course, Errors errors)
    {
        if(errors.hasErrors())
        {
            return "editcourse";
        }
        Course editedCourse = courseRepo.findFirstByCourseId(id);
        editedCourse.setCourseNumber(course.getCourseNumber());
        editedCourse.setDept(course.getDept());
        editedCourse.setTitle(course.getTitle());
        editedCourse.setPrereqs(course.getPrereqs());
        editedCourse.setCourseId(course.getCourseId());
        editedCourse.setRepo(courseRepo); // This is weird, but it's necessary to save the prereqs correctly with the course. At least that's how I remember it...
        editedCourse.saveToDb();
        return "courselist";
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
