package net.travisford.courseomatic;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CourseTile {
// Note: The convention used across all Course-related classes: course name is a String concatenating department and course number: Department + "-" + Number. Ex: "CS-201".
// The full title of the course, "Programming 1" for example, is referred to as the course "title"
    public final String Dept;
    public final String Number;
    public final String Title;
    public final ArrayList<String> PrereqNames;
}
