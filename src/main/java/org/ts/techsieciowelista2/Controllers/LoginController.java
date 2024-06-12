package org.ts.techsieciowelista2.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.ts.techsieciowelista2.LoginForm;
import org.ts.techsieciowelista2.dto.LoginDto;
import org.ts.techsieciowelista2.dto.UserDetailsDto;
import org.ts.techsieciowelista2.service.LoginService;

import java.security.Principal;
import java.util.Collection;

/**
 * Login controller
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {
    private final LoginService loginService;

    /**
     * @param loginService
     */
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * @param loginForm
     * @return information if login data are correct
     */
    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        String token = loginService.userLogin(loginForm);
        if (token == null) {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(new LoginDto(token), HttpStatus.OK);
        }
    }
//    @GetMapping("/user-details")
//    public ResponseEntity<?> getUserDetails() throws JsonProcessingException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        Object principal = authentication.getPrincipal();
//
//        Integer userId = Integer.parseInt(principal.toString().split("userId=")[1].split(",")[0]);
//        if(!loginService.userExists(userId)) {
//            return new ResponseEntity<>("Invalid user", HttpStatus.UNAUTHORIZED);
//        }
//
//        String role = authorities.stream()
//                .map(GrantedAuthority::getAuthority)
//                .findFirst()
//                .orElse("ROLE_USER");
//
//        return ResponseEntity.ok(new UserDetailsDto(userId, role));
//    }
    @GetMapping("/user-role")
    public ResponseEntity<String> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");

        return ResponseEntity.ok(role);
}
}
