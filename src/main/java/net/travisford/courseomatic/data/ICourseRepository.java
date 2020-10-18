package net.travisford.courseomatic.data;
import net.travisford.courseomatic.Course;
import org.springframework.data.repository.CrudRepository;


//Reminder, Spring creates a class implementation of this interface at compile time.
public interface ICourseRepository extends CrudRepository<Course, Long>
{
    public Course findFirstByDeptAndCourseNumber(String dept, String number);
}
