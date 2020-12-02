package net.travisford.courseomatic.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="courses")
public class CourseProperties
{
    private int courseDisplayLimit;
}
