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
public class DeleteCourseController {

    private ICourseRepository courseRepo;

    @Autowired
    public DeleteCourseController(ICourseRepository courseRepo)
    {
        this.courseRepo = courseRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping("deletecourse/{courseId}")
    public String showDeleteCourse(@PathVariable("courseId") long id, Model model)
    {
        Course course = courseRepo.findById(id).get();
        model.addAttribute("course", course);
        boolean hasPrereqs = false;
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(c->courses.add(c));
        hasPrereqs = courses.stream().anyMatch(c->c.getPrereqIds().stream().anyMatch(p->p.equals(Long.toString(id))));
        model.addAttribute("hasPrereqs", hasPrereqs);

        return "deletecourse";
    }

    @PostMapping("deletecourse/{courseId}")
    public String deleteCourse(@PathVariable("courseId") long id, @Valid @ModelAttribute("course") Course course, Errors errors)
    {
        if(errors.hasErrors())
        {
            return "deletecourse";
        }
        Course deletedCourse = courseRepo.findFirstByCourseId(id);

        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(c->courses.add(c));
        courses.forEach(c->c.getPrereqs().removeIf(p->p.getCourseId() == id));
        courses.forEach(c->c.setRepo(courseRepo)); //sigh...
        courses.forEach(c->c.saveToDb());

        deletedCourse.setRepo(courseRepo); //sigh...
        deletedCourse.deleteFromDb();
        return "courselist";
    }



//    @ModelAttribute
//    private void addAttributes(Model model)
//    {
//        List<Course> courses =  createCourseList();
//        model.addAttribute("courses", courses);
//
//    }

//    private List<Course> createCourseList(){
//
//        ArrayList<Course> output = new ArrayList<>();
//        courseRepo.findAll().forEach(x -> output.add(x));
//        return output;

}
