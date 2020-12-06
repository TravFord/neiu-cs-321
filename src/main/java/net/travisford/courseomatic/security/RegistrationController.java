package net.travisford.courseomatic.security;

import lombok.extern.slf4j.Slf4j;
import net.travisford.courseomatic.CourseOfStudy;
import net.travisford.courseomatic.data.CourseOfStudyRepository;
import net.travisford.courseomatic.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController
{
    private UserRepository userRepo;
    private CourseOfStudyRepository studyRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder, CourseOfStudyRepository studyRepo)
    {
        this.userRepo = userRepo;
        this.studyRepo = studyRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm()
    {
        return "registration";
    }

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm addRegistrationFormToModel()
    {
        return new RegistrationForm();
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm, Errors errors)
    {
        if(errors.hasErrors())
        {
            return "registration";
        }
        if(userRepo.existsByUsernameIgnoreCase(registrationForm.getUsername()) == false) {
            userRepo.save(registrationForm.toUser(passwordEncoder));
            studyRepo.save(new CourseOfStudy(userRepo.findByUsernameIgnoreCase(registrationForm.getUsername())));
            return "redirect:/login";
        }
        return "registration";
    }
}