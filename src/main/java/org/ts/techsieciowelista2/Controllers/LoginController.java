package org.ts.techsieciowelista2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ts.techsieciowelista2.LoginForm;
import org.ts.techsieciowelista2.service.LoginService;

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
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        String token = loginService.userLogin(loginForm);
        if (token == null) {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }


}
