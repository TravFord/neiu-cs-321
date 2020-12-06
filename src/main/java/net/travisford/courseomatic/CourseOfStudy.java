package net.travisford.courseomatic;

import lombok.*;
import net.travisford.courseomatic.security.User;

import javax.persistence.*;
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

    @ElementCollection
    private final Map<Course, Integer> courses = new HashMap<>() ;

    public CourseOfStudy(User user)
    {
        this.user = user;
    }

    protected CourseOfStudy()
    {
        this.user=null;
    }

    public void upsertCourse(Course course, int sequence)
    {
        courses.putIfAbsent(course, sequence);
        courses.replace(course, sequence);
    }

    public void removeCourse(Course course)
    {
        courses.remove(course);
    }

    public int getSequence(Course course)
    {
        if(courses.containsKey(course))
        {
            return courses.get(course);
        }
        else
        {
            return -1;
        }
    }

    public ArrayList<Course> getCoursesBySequence(int seq)
    {
        ArrayList<Course> outCourses = new ArrayList<>();
        for(Course c : courses.keySet())
        {
            if(courses.get(c).equals(seq))
            {
                outCourses.add(c);
            }
        }

        return outCourses;
    }
}




