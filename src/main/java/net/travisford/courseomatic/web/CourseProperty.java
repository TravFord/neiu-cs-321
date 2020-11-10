package net.travisford.courseomatic.web;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CourseProperty
{
    int limit = 6;
}
