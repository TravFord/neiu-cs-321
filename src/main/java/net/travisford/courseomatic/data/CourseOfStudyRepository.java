package net.travisford.courseomatic.data;

import net.travisford.courseomatic.CourseOfStudy;
import net.travisford.courseomatic.security.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CourseOfStudyRepository extends CrudRepository<CourseOfStudy, Long>
{
    @Override
    List<CourseOfStudy> findAll();

    @Override
    Optional<CourseOfStudy> findById(Long aLong);
}
