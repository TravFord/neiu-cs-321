package net.travisford.courseomatic;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class Course {
// Note: The convention used across all Course-related classes: course name is a String concatenating department and course number: Department + "-" + Number. Ex: "CS-201".
// The full title of the course, "Programming 1" for example, is referred to as the course "title"
    @Getter @Setter private Long courseId;
    @Getter @Setter private String courseNumber;
    @Getter @Setter private String dept;
    @Getter @Setter private String title;
    @Getter @Setter private ArrayList<Course> prereqs;
    @Getter @Setter private ArrayList<String> prereqsDeptAndNumber;
    @Getter @Setter private String newPrereq;
    private CourseRepository repo;
    @Getter private ArrayList<String> attributes;

    public Course(Long courseID, String courseNumber, String courseDept, String title, ArrayList<Course> prereqs, ArrayList<String> attributes, CourseRepository repo) {
        this.courseId = courseID;
        this.courseNumber = courseNumber;
        this.dept = courseDept;
        this.title = title;
        this.prereqs = prereqs;
        this.attributes = attributes;
        this.repo = repo;
    }

    public Course(CourseRepository repo)
    {
        this((long) 0, "", "", "", new ArrayList<Course>(), new ArrayList<String>(), repo);
    }

    public Course()
    {
        this((long) 0, "", "", "", new ArrayList<Course>(), new ArrayList<String>(), null);
    }

//    public Course(SimpleCourse simpleCourse)
//    {
//        this(simpleCourse.);
//    }


    public ArrayList<String> getPrereqTitles() {
        ArrayList<String> output = new ArrayList<>();
        for(Course c : prereqs)
        {
            output.add(c.title);
        }
        return output;
    }


    public ArrayList<String> getPrereqsDeptAndNumber() {
        ArrayList<String> output = new ArrayList<>();
        for(Course c : prereqs)
        {
            output.add(c.dept + "-" + c.courseNumber);
        }
        return output;
    }


    public String getDeptAndNumber() {
        return this.dept + "-" + this.courseNumber;
    }

    public Boolean addPrereq(Course course){
        if(prereqs.stream().anyMatch(x -> x.dept.equals(course.dept) && x.courseNumber.equals(course.courseNumber)) == false)
        {
            prereqs.add(course);
        }
        return true;
    }

    public Boolean addPrereq(String courseName) // a courseName = CS-201, not 201 or Programming 1
    {
        ArrayList<Course> courses = new ArrayList<>();
        repo.findAll().forEach(x-> courses.add(x));

        return addPrereqs(courses, new ArrayList<String>(List.of(courseName)));
    }

    public Boolean addPrereq(List<Course> courses, String courseName) // a courseName = CS-201, not 201 or Programming 1
    {
        return addPrereqs(courses, new ArrayList<String>(List.of(courseName)));
    }


    public Boolean addPrereqs(List<Course> courses, List<String> prereqCourseNames) // a courseName = CS-201, not 201 or Programming 1
    {
        Boolean allAdded = true;

        for(String prereqCourseName : prereqCourseNames) {
            if(courses.stream()
                    .anyMatch(k -> k.getDeptAndNumber().equals(prereqCourseName)) // If there's actually a Course object with that name
                && prereqs.stream()
                    .anyMatch(k -> k.getDeptAndNumber().equals(prereqCourseName)) == false) // and the prereq course isn't already listed as a prereq
            {
                prereqs.add(courses.stream()
                        .filter(k -> k.getDeptAndNumber().equals(prereqCourseName))
                        .findFirst()
                        .get());
            }
            else
            {
                allAdded = false;
            }
        }

        return allAdded;
    }


//    public static Boolean addPrereqToCourse(List<Course> courses, String targetCourseName, String prereqCourseName)
//    {
//                if(courses.stream()
//                .anyMatch(k -> k.getDeptAndNumber().equals(targetCourseName)))
//                {
//                    courses.stream()
//                            .filter(k -> k.getDeptAndNumber().equals(targetCourseName))
//                            .findFirst()
//                            .get()
//                            .addPrereq(courses, prereqCourseName);
//                    return true;
//                }
//
//                return false;
//    }

    public void saveToDb()
    {
        repo.save(this);
    }

    //TODO: remove setter hack when you learn a better way
    public String getNewPrereq() {
        return "Course_Java_line147";
    }
    //TODO: remove setter hack when you learn a better way
    public void setNewPrereq(String newPrereq) {
        this.addPrereq(newPrereq);
    }

    public List<String> getNewPrereqs() {
        return new ArrayList<String>();
    }
    //TODO: remove setter hack when you learn a better way
    public void setNewPrereqs(List<String> newPrereqs) {
        newPrereqs.forEach(x -> this.addPrereq(x));
    }


}
