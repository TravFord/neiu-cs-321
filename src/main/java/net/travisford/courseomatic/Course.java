package net.travisford.courseomatic;

import lombok.Data;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Course {
    @Getter private final String courseId;
    @Getter @Setter private String courseNumber;
    @Getter @Setter private String dept;
    @Getter @Setter private String friendlyName;
    @Getter @Setter private ArrayList<Course> prereqs;
    @Getter private ArrayList<String> prereqsDeptAndNumber;
    @Getter private ArrayList<String> attributes;

    public Course(String courseID, String courseNumber, String courseDept, String friendlyName, ArrayList<Course> prereqs, ArrayList<String> attributes) {
        this.courseId = courseID;
        this.courseNumber = courseNumber;
        this.dept = courseDept;
        this.friendlyName = friendlyName;
        this.prereqs = prereqs;
        this.attributes = attributes;
    }

    public ArrayList<String> getPrereqFriendlyNames() {
        ArrayList<String> output = new ArrayList<>();
        for(Course c : prereqs)
        {
            output.add(c.friendlyName);
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



    public Boolean addPrereqs(List<Course> courses, List<String> prereqCourseNames) // a courseName = CS-201, not 201 or Programming 1
    {
        Boolean allAdded = true;

        for(String prereqCourseName : prereqCourseNames) {
            if(courses.stream()
                    .anyMatch(k -> k.getDeptAndNumber() == prereqCourseName)
                && prereqs.stream()
                    .anyMatch(k -> k.getDeptAndNumber() == prereqCourseName) == false)
            {
                prereqs.add(courses.stream()
                        .filter(k -> k.getDeptAndNumber() == prereqCourseName)
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

    public Boolean addPrereq(List<Course> courses, String courseName) // a courseName = CS-201, not 201 or Programming 1
    {
        return addPrereqs(courses, new ArrayList<String>(List.of(courseName)));
    }

    public static Boolean addPrereqToCourse(List<Course> courses, String targetCourseName, String prereqCourseName)
    {
                if(courses.stream()
                .anyMatch(k -> k.getDeptAndNumber() == targetCourseName))
                {
                    courses.stream().findFirst().get().addPrereq(courses, prereqCourseName);
                    return true;
                }

                return false;
    }
}
