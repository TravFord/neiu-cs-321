package net.travisford.courseomatic.web;

import net.travisford.courseomatic.Course;
import net.travisford.courseomatic.CourseOfStudy;
import net.travisford.courseomatic.data.CourseOfStudyRepository;
import net.travisford.courseomatic.data.CourseProperties;
import net.travisford.courseomatic.data.UserRepository;
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
    private CourseOfStudyRepository studyRepo;
    private UserRepository userRepo;
    @Autowired
    public CourseListController(ICourseRepository courseRepo, CourseProperties properties, CourseOfStudyRepository studyRepo, UserRepository userRepo)
    {
       this.properties = properties;
        this.studyRepo = studyRepo;
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        Course.seedCourses(courseRepo);
    }

    @GetMapping
    public String showCourseList()
    {
        return "courselist";
    }

    @DeleteMapping("/delete/courseId")
    public String processDeleteCourse(@PathVariable("courseId") long courseId, @Valid @ModelAttribute("course") Course course, Errors errors)
    {
        if(errors.hasErrors())
        {
            return "courselist";
        }

        Course deletedCourse = courseRepo.findById(courseId).get();
        deletedCourse.deleteFromDb();

        return "redirect:/courselist";
    }

    @PostMapping("/{courseId}")
    public String scheduleCourse(@PathVariable("courseId") long courseId, @Valid @ModelAttribute("course") Course course, Errors errors)
    {
        if(errors.hasErrors())
        {
            return "courselist";
        }
        User  user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Course scheduledCourse = courseRepo.findFirstByCourseId(courseId);
        ArrayList<CourseOfStudy> debugRepos = new ArrayList<CourseOfStudy>(studyRepo.findAll());
        ArrayList<User> debugUsers = new ArrayList<User>();
        userRepo.findAll().forEach(x->debugUsers.add(x));

        if(!studyRepo.findAll().stream().anyMatch(x->x.getUser().getId() == user.getId()))
        {
            studyRepo.save(new CourseOfStudy(user));
        }
        CourseOfStudy cos = studyRepo.findAll().stream().filter(x->x.getUser().getId() == user.getId()).findAny().get();
        cos.addCourse(scheduledCourse);
        cos = studyRepo.findAll().stream().filter(x->x.getUser().getId() == user.getId()).findAny().get();

        return "redirect:/courselist";
    }

    @ModelAttribute
    public void addAttributes(Model model)
    {
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(course -> {courses.add(course);});
        model.addAttribute("courses", courses);
       // model.addAttribute("selectedCourse", new Course());
    }

    private List<Course> createCourseList(){
        List<Course> courses = new ArrayList<Course>();
        courseRepo.findAll().forEach(x -> courses.add(x));

        return courses;
    }
}
