//package net.travisford.courseomatic;
//
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@RequiredArgsConstructor
//@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)
//@Entity
//@Table(name="Course")
//public class PersistableCourse
//{
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    private long courseId;
//    private String title;
//    private String courseNumber;
//    private String dept;
//
//    @ManyToMany(targetEntity = PersistableCourse.class)
//    private List<PersistableCourse> prereqs;
//}
