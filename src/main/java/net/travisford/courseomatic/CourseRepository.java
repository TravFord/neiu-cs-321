package net.travisford.courseomatic;

public interface CourseRepository {
    Iterable<Course> findAll();
    Course findOne(long courseId);
    void save(Course course);
}
