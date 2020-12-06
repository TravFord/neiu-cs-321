package net.travisford.courseomatic;

import lombok.*;
import net.travisford.courseomatic.security.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Data
@Entity
public class CourseOfStudy
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private final User user;

    @Getter
    private String accessDate;

    @OneToMany
    private final List<Course> courses;


    public CourseOfStudy(User user)
    {
        this.user = user;
        accessDate = Instant.now().toString();
        courses = new ArrayList<>();
    }


    protected CourseOfStudy()
    {
        this.user=null;
        courses = new ArrayList<>();
        accessDate = Instant.now().toString();
    }


    public void addCourse(Course course)
    {
        if(!courses.contains(course))
        {
            courses.add(course);
        }

        accessDate = Instant.now().toString();
    }


    public void removeCourse(Course course)
    {
        courses.remove(course);
    }

    public void updateAccessDate()
    {
        accessDate = Instant.now().toString();
    }


//    public int getSequence(Course course)
//    {
//        if(courses.containsKey(course))
//        {
//            return courses.get(course);
//        }
//        else
//        {
//            return -1;
//        }
//    }


//    public ArrayList<Course> getCoursesBySequence(int seq)
//    {
//        ArrayList<Course> outCourses = new ArrayList<>();
//        for(Course c : courses.keySet())
//        {
//            if(courses.get(c).equals(seq))
//            {
//                outCourses.add(c);
//            }
//        }
//
//        return outCourses;
//    }
}




