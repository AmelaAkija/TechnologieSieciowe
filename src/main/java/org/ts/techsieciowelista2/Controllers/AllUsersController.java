package org.ts.techsieciowelista2.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.ts.techsieciowelista2.Repositories.UserRepository;
import org.ts.techsieciowelista2.User;

import java.util.Optional;

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
     * @param
     * @return user saved in user repository
     * @throws ResponseStatusException If a user with the same username already exists
     */
    @GetMapping("/SearchBy/ID/{id}")
    public Optional<User> searchById(@PathVariable int id) {
        return userRepository.findById(id);
    }
    @PostMapping("/Add")
    @PreAuthorize("hasRole('LIBRARIAN')")
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
    @DeleteMapping("/deleteUser/{userId}")
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
    @PutMapping("/update/{userId}")
    @Transactional
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<String> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        return userRepository.findById(userId).map(existingUser -> {
            // Check if username is being updated and if it already exists in another user
            if (user.getUsername() != null && !user.getUsername().equals(existingUser.getUsername())) {
                if (userRepository.existsByUsername(user.getUsername())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
                }
                existingUser.setUsername(user.getUsername());
            }

            if (user.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            if (user.getMail() != null) {
                existingUser.setMail(user.getMail());
            }

            if (user.getFullusername() != null) {
                existingUser.setFullusername(user.getFullusername());
            }

            userRepository.save(existingUser);

            return ResponseEntity.ok("User with id " + userId + " has been updated");
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " not found"));
    }


}
