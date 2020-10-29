package net.travisford.courseomatic.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm
{
    @NotNull
    @Size(min = 5, message = "Username must have at least 5 characters")
    private String username;

    @NotNull
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @NotNull
    @NotEmpty(message = "First and last name are required")
    private String fullname;

    @NotNull
    @NotEmpty(message = "Email address is required")
    @Email(message = "Address must be in a proper format: \"abcd@xyz.com\"")
    private String email;

    public User toUser(PasswordEncoder passwordEncoder)
    {

        return new User(username, passwordEncoder.encode(password), fullname, email);
    }


}
