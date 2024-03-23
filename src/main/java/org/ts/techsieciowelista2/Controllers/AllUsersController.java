package org.ts.techsieciowelista2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.UserRepository;
import org.ts.techsieciowelista2.User;

@RestController
@RequestMapping("/users")
@ResponseStatus(code = HttpStatus.CREATED)
public class AllUsersController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AllUsersController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/Add")
    @PreAuthorize("hasRole('LIBRARIAN')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    String removeUser(@PathVariable Integer userId){

        userRepository.deleteById(userId);
        return "User with id " + userId + " has been successfully deleted";
    }

}
