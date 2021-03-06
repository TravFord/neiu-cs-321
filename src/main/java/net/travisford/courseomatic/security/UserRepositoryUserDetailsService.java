package net.travisford.courseomatic.security;

import net.travisford.courseomatic.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService
{
    private UserRepository userRepo;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepo)
    {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        User user = userRepo.findByUsernameIgnoreCase(userName);
        if(user != null)
        {
            return user;
        }
        throw new UsernameNotFoundException("User " + userName + " is not found");
    }

    public boolean UserExists(String username)
    {
        if(userRepo.existsByUsernameIgnoreCase(username))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
