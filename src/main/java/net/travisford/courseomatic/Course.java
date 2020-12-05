package net.travisford.courseomatic;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.travisford.courseomatic.data.ICourseRepository;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class Course {
// Note: The convention used across all Course-related classes: course name is a String concatenating department and course number: Department + "-" + Number. Ex: "CS-201".
// The full title of the course, "Programming 1" for example, is referred to as the course "title"
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long courseId;

    @Getter @Setter
    private String courseNumber;

    @Getter @Setter
    private String dept;

    @Getter @Setter
    private String title;

    @Getter @Setter @ManyToMany
    private List<Course> prereqs;

    @Getter @Setter @Transient
    private List<String> prereqsDeptAndNumber;

    @Getter @Setter @Transient
    private String newPrereq;

    @Transient
    private ICourseRepository repo;
    //@Getter @Setter private List<String> attributes;

    public Course(String courseNumber, String courseDept, String title, ArrayList<Course> prereqs
            //, ArrayList<String> attributes
            , ICourseRepository repo) {
        this.courseNumber = courseNumber;
        this.dept = courseDept;
        this.title = title;
        this.prereqs = prereqs;
        //this.attributes = attributes;
        this.repo = repo;
    }

    public Course(ICourseRepository repo)
    {
        this("", "", ""
                , new ArrayList<Course>()
                //, new ArrayList<String>()
                , repo);
    }

    public Course()
    {
        this("", "", ""
                , new ArrayList<Course>()
                //, new ArrayList<String>()
                , null);
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

    public ArrayList<String> getPrereqIds() {
        ArrayList<String> output = new ArrayList<>();
        for(Course c : prereqs)
        {
            output.add(c.courseId.toString());
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


    public static void addPrereqToCourse(List<Course> courses, String targetCourseName, String prereqCourseName)
    {
                if(courses.stream()
                .anyMatch(k -> k.getDeptAndNumber().equals(targetCourseName)))
                {
                    courses.stream()
                            .filter(k -> k.getDeptAndNumber().equals(targetCourseName))
                            .findFirst()
                            .get()
                            .addPrereq(courses, prereqCourseName);
                }
    }

    public static void addPrereqsToCourse(List<Course> courses, String targetCourseName, String prereqList)
    {
        List<String> prereqs = Arrays.asList(prereqList.split(","));

        for(String p : prereqs)
        {
            addPrereqToCourse(courses, targetCourseName, p);
        }
    }


    public static Course findCourse(List<Course> courses, String courseNumber, String dept)
    {
        if(courses.stream().anyMatch(x -> x.courseNumber.equals(courseNumber) && x.dept.equals(dept)))
        {
            Course course = courses.stream().filter(x -> x.courseNumber.equals(courseNumber) && x.dept.equals(dept)).findFirst().get(); // holy methods
            return course;
        }

        return null;
    }

    //TODO: remove setter hack when you learn a better way
    public String getNewPrereq() {
        return "Course_Java_line202";
    }
    //TODO: remove setter hack when you learn a better way
    public void setNewPrereq(String newPrereq) {
        this.addPrereq(newPrereq);
    }

    //TODO: remove setter hack when you learn a better way
    public List<String> getNewPrereqs() {
        return new ArrayList<String>();
    }
    //TODO: remove setter hack when you learn a better way
    public void setNewPrereqs(List<String> newPrereqs) {
        newPrereqs.forEach(x -> this.addPrereq(x));
    }


    public static void seedCourses(ICourseRepository courseRepo) {

        if (courseRepo.count() == 0)
        {
            List<String> courseStrings = new ArrayList<String>(Arrays.asList
                    (
                            "200;CS;Programming 1;MATH-173"
                            ,"201;CS;Discrete Structures;MATH-173"
                            ,"207;CS;Programming 2;CS-200,CS-201"
                            ,"300;CS;Client Side Web Development;CS-201"
                            ,"301;CS;Computer Organization;"
                            ,"304;CS;Data Structures;CS-201,CS-207"
                            ,"308;CS;Operating Systems;CS-207,CS-301"
                            ,"315;CS;Modern Database Management;CS-207"
                            ,"319;CS;Software Engineering;CS-304"
                            ,"321;CS;Server Side Web Development;CS-207,CS-300"
                            ,"324;CS;Intro To Design Of Algorithms;CS-304"
                            ,"173;MATH;Some Math Class;"
                            ,"300;CS;Server Side Web Development;"
                            ,"300;CS;Server Side Web Development;"
                            ,"300;CS;Server Side Web Development;"
                    )
            );

            Course.regenerateCourses(courseRepo, courseStrings);
        }
    }


    public static void regenerateCourses(ICourseRepository repo, List<String> courses) // "NNN;AA;title;AA-NNN,AA-NNN,AA-NNN"
    {
        ArrayList<Course> crs = new ArrayList<>();

        try {
            // Create courses
            for (String c : courses)
            {
                String[] parts = c.split(";");

                if(parts.length >= 3)
                {
                    String courseNumber = parts[0];
                    String dept = parts[1];
                    String title = parts[2];

                    crs.add(new Course(courseNumber, dept, title, new ArrayList<Course>(), repo));
                }
            }

            for (Course c : crs) {
                c.saveToDb(); // We need to save before adding prereqs to avoid "object references an unsaved transient instance" error when a course with prereqs is saved before a prereq course is saved
            }

            //Assign prereqs
            for (String c : courses)
            {
                String[] parts = c.split(";");
                String courseNumber = parts[0];
                String dept = parts[1];
                Course course = Course.findCourse(crs, courseNumber, dept);

                if(parts.length > 3 && course != null) // Are there prereqs? Is there a course to assign to?
                {
                    String[] prereqs = parts[3].split(","); // Prereqs are comma delimited AA-NNN,AA-NNN
                    if (prereqs.length > 0) { //... are there really prereqs?
                        for (String p : prereqs) {
                            String prereqDept = p.split("-")[0];
                            String prereqNumber = p.split("-")[1];
                            Course prereqCourse = Course.findCourse(crs, prereqNumber, prereqDept);
                            if(prereqCourse != null)
                            {
                                course.prereqs.add(prereqCourse);
                            }
                        }
                    }
                }
            }

            for (Course c : crs) {
                c.saveToDb(); // Save will upsert if the course exists in DB already
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error generating Courses: " + ex);
        }
    }


    public void saveToDb()
    {
        Course course = repo.findFirstByDeptAndCourseNumber(this.dept, this.courseNumber);
        if(course != null)
        {
            this.setCourseId(course.getCourseId());
        }

        repo.save(this);
    }

    public void deleteFromDb()
    {
        Course course = repo.findFirstByDeptAndCourseNumber(this.dept, this.courseNumber);
        if(course != null)
        {
            this.setCourseId(course.getCourseId());
        }

        repo.delete(this);
    }

}
