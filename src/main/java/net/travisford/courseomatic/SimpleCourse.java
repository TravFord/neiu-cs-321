package net.travisford.courseomatic;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class SimpleCourse {
// Note: The convention used across all Course-related classes: course name is a String concatenating department and course number: Department + "-" + Number. Ex: "CS-201".
// The full title of the course, "Programming 1" for example, is referred to as the course "title"

    @NotNull(message = "Department is required") @NotBlank(message = "Department is required") @Getter @Setter private String dept;
    @NotNull(message = "Number is required") @NotBlank(message = "Number is required") @Getter @Setter private String number;
    @NotNull(message = "Title is required") @NotBlank(message = "Title is required") @Getter @Setter private String title;
    @Getter @Setter private ArrayList<String> prereqNames;

    public SimpleCourse(String dept, String number, String title, ArrayList<String> prereqNames){
        this.dept = dept;
        this.number = number;
        this.title = title;
        this.prereqNames = prereqNames;
    }

    public SimpleCourse(){
        this("","","", new ArrayList<String>());
    }


}
