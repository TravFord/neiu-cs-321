package net.travisford.courseomatic.data;
import net.travisford.courseomatic.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


//Reminder, Spring creates a class implementation of this interface at compile time.
public interface ICourseRepository extends CrudRepository<Course, Long>
{
    Course findFirstByDeptAndCourseNumber(String dept, String number);

    Course findFirstByCourseId(long courseId);

    void deleteByCourseId(long courseId);
}
