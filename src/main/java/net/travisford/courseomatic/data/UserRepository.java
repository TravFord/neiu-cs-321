package net.travisford.courseomatic.data;

import net.travisford.courseomatic.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<org.apache.catalina.User, Long> {
    User findByUsername(String name);
}
