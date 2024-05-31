package org.ts.techsieciowelista2.Controllers;

import jakarta.transaction.Transactional;
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

/**
 * User controller
 */
@RestController
@RequestMapping("/users")
@ResponseStatus(code = HttpStatus.CREATED)
public class AllUsersController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * @param userRepository
     * @param passwordEncoder
     */
    @Autowired
    public AllUsersController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param user
     * @return user saved in user repository
     * @throws ResponseStatusException If a user with the same username already exists
     */
    @PostMapping("/Add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User userExists = userRepository.findByUsername(user.getUsername());
        if (userExists != null) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User " + user.getUsername() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }
    /**
     * @return all users
     */
    @GetMapping("/GetAll")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @param userId id of user to be deleted
     * @return information i user was deleted
     * @throws ResponseStatusException If user with id is not in database
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    String removeUser(@PathVariable Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User with id " + userId + " has been successfully deleted";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found");
        }
    }
    /**
     * @param userId id of user to be modified
     * @return information if user was updated
     * @throws ResponseStatusException If user with id is not in database
     */
    @PutMapping("/{userId}")
    @Transactional
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<String> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        if (userRepository.existsById(userId)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.updateUser(userId, user.getUsername(), user.getPassword(), user.getMail(), user.getFullusername());

        return ResponseEntity.ok("User with id " + userId + " has been updated");}
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found");
        }
    }

}
