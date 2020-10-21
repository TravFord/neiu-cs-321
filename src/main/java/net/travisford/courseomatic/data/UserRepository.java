package net.travisford.courseomatic.data;

import net.travisford.courseomatic.security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String name);
}
