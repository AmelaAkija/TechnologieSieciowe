//obsługa usera w AllUsersController!!!

//package org.ts.techsieciowelista2.Controllers;
//
//import org.ts.techsieciowelista2.Repositories.UserRepository;
//import org.ts.techsieciowelista2.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/User")
//public class UserController {
//    private final UserRepository userRepository;
//    @Autowired
//    public UserController(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }
//    @PostMapping("/Add")
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public User addUser(@RequestBody User user) {
//        return userRepository.save(user);
//    }
//    @GetMapping("/GetAll")
//    public @ResponseBody Iterable<User> getAllUsers(){
//        return userRepository.findAll();
//    }
//}
//
