package net.travisford.courseomatic;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcCourseRepository implements CourseRepository{

    private JdbcTemplate jdbc;
    private SimpleJdbcInsert courseInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcCourseRepository (JdbcTemplate jdbc){
        this.jdbc = jdbc;
        this.courseInserter = new SimpleJdbcInsert(jdbc).withTableName("Courses").usingGeneratedKeyColumns("courseId");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Iterable<Course> findAll() {
        //https://stacktips.com/tutorials/spring/query-database-using-jdbctemplate-in-spring#40-querying-for-multiple-rows
        List<Course> courses = new ArrayList<Course>();
        List<Map<String, Object>> returnedRows = jdbc.queryForList("SELECT courseID, title, courseNumber, dept FROM Courses");

        for (Map<String, Object> row : returnedRows) {
            Course course = new Course(
                    (long)row.get("courseId"),
                    (String)row.get("courseNumber"),
                    (String)row.get("dept"),
                    (String)row.get("title"),
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
        Course newCourse = jdbc.queryForObject("SELECT courseID, title, courseNumber, dept FROM Courses WHERE courseID = ?"
                , this::mapRowToCourse
                , courseId );

        ArrayList<Course> courses = new ArrayList<>();
        // https://www.baeldung.com/java-iterable-to-collection
        this.findAll().forEach(courses::add);
        newCourse.addPrereqs(courses, getPrereqs(courseId));
        return newCourse;
    }

    @Override
    public void save(Course course) {

        //pack up course data for write to DB
        @SuppressWarnings("unchecked")
        Map<String, Object> values =
                objectMapper.convertValue(course, Map.class);
        values.put("title", course.getTitle());
        values.put("courseNumber", course.getCourseNumber());
        values.put("dept", course.getDept());

        // Course doesn't exist, create course and grab ID
        if(jdbc.queryForObject("SELECT COUNT(*) FROM Course WHERE courseNumber = ? AND dept = ?", Integer.class , course.getCourseNumber(), course.getDept() ) == 0) // Course does not yet exist
        {
            long newCourseID = courseInserter.executeAndReturnKey(values)
                            .longValue();
        }
        // Course already exists. Update
        else
        {
            jdbc.update("UPDATE Course SET title = ?, courseNumber = ?, dept = ? WHERE courseId = ?", course.getTitle(), course.getCourseNumber(), course.getDept(), course.getCourseId());
        }

        // Remove and re-add DB prereqs
        jdbc.update("DELETE FROM Course_Prereq WHERE courseId = ?", course.getCourseId());
        course.getPrereqs().forEach(
                x -> jdbc.update("INSERT IGNORE INTO Course_Prereq (courseId, prereqId) VALUES (?,?)  ", course.getCourseId(), x.getCourseId()));
    }

    private List<String> getPrereqs(long courseId){
        List<String> courseNames = new ArrayList<String>();

        // Get all prereq names (ex: CS-101) from the DB
        List<Map<String, Object>> returnedRows = jdbc.queryForList("SELECT c.dept, c.courseNumber, " +
                "   FROM Courses c INNER JOIN " +
                "   Course_Prereq cp ON c.courseId = cp.prereqId" +
                "   WHERE cp.courseID = ?", courseId );

        for (Map<String, Object> row : returnedRows) {
            String courseName = row.get("dept") + "-" + row.get("courseNumber");
            courseNames.add(courseName);
        }

        return courseNames;
    }

    private Course mapRowToCourse(ResultSet rs, int rowNum) throws SQLException {
       return new Course(
                       Long.parseLong(rs.getString("courseId")),
                       rs.getString("title"),
                       rs.getString("courseNumber"),
                       rs.getString("dept"),
                       new ArrayList<Course>(),
                       new ArrayList<String>()
       );
    }
}
