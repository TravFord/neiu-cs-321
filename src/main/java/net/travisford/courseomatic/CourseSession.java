//package net.travisford.courseomatic;
//
//import lombok.Data;
//import lombok.NonNull;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//
//@Data
//public class CourseSession {
//// Note: The convention used across all Course-related classes: course name is a String concatenating department and course number: Department + "-" + Number. Ex: "CS-201".
//// The full title of the course, "Programming 1" for example, is referred to as the course "title"
//    private final Course course;
//    @NonNull
//    private int semesterSeq;
//    @NonNull
//    private String semesterFriendlyName;
//
//    public Boolean prereqsMet(ArrayList<CourseSession> sessions)
//    {
//        for(Course prereq : this.course.getPrereqs()) {                                             // For each prerequisite of the course associated with this session,
//            if (sessions.stream().noneMatch(k -> k.course.getCourseId().equals(prereq.getCourseId())     // see if any session in the list passed in has the courseID
//                    && k.semesterSeq < this.semesterSeq))                                           // of the prereq, and also was taken in a prior semester to this session's semester
//            {
//                return false;
//            }
//        }
//
//        return true;
//    }
//}