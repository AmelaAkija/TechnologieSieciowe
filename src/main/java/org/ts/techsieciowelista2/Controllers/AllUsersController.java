package org.ts.techsieciowelista2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.UserRepository;
import org.ts.techsieciowelista2.User;

import java.util.Collections;
import java.util.List;

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
    public User addUser(@RequestBody User user){
        User userExists = userRepository.findByUsername(user.getUsername());
        if (userExists != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User " + user.getUsername() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    String removeUser(@PathVariable Integer userId){
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User with id " + userId + " has been successfully deleted";}
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found");
        }
    }

}
