package net.travisford.courseomatic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import net.travisford.courseomatic.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCourseRepository implements CourseRepository{

    private JdbcTemplate jdbc;

    @Override
    public Iterable<Course> findAll() {
        //https://stacktips.com/tutorials/spring/query-database-using-jdbctemplate-in-spring#40-querying-for-multiple-rows
        List<Course> courses = new ArrayList<Course>();
        List<Map<String, Object>> returnedRows = jdbc.queryForList("SELECT c.courseID, c.title, c.courseNumber, c.dept \n" +
                "   FROM Courses c INNER JOIN \n" +
                "   Course_Prereq cp ON c.courseId = cp.prereqId\n" +
                "   WHERE cp.courseID = courseID" );

        for (Map<String, Object> row : returnedRows) {
            Course course = new Course(
                    (long)row.get("courseId"),
                    (String)row.get("title"),
                    (String)row.get("courseNumber"),
                    (String)row.get("dept"),
                    new ArrayList<Course>(),
                    new ArrayList<String>()
            );
            courses.add(course);
        }

        for(Course c : courses)
        {
            c.addPrereqs(courses, getPrereqs(c.getCourseId()));
        }

        return courses;
    }

    @Override
    public Course findOne(long courseId) {
        return null;
    }

    @Override
    public Boolean save(Course course) {
        return false;
    }

    private List<String> getPrereqs(long courseId){
        List<String> courseNames = new ArrayList<String>();

        List<Map<String, Object>> returnedRows = jdbc.queryForList("SELECT c.dept, c.courseNumber, " +
                "   FROM Courses c INNER JOIN " +
                "   Course_Prereq cp ON c.courseId = cp.prereqId" +
                "   WHERE cp.courseID = courseID" );

        for (Map<String, Object> row : returnedRows) {
            String courseName = (String)row.get("dept") + "-" + (String)row.get("courseNumber");
            courseNames.add(courseName);
        }

        return courseNames;
    }
}
