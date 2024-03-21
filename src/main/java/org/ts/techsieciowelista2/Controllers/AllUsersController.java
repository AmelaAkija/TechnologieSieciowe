package org.ts.techsieciowelista2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.Repositories.UserRepository;
import org.ts.techsieciowelista2.User;

@RestController
@RequestMapping("/users")
public class AllUsersController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AllUsersController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/Add")
    //@PreAuthorize("hasRole('LIBRARIAN')")
    @PreAuthorize("permitAll()")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @PostMapping("/Remove")
    @PreAuthorize("permitAll()")
    public void removeUser(@PathVariable Integer userId){
        userRepository.removeUserBy(userId);
    }

}
