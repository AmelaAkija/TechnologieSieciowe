package org.ts.techsieciowelista2.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ts.techsieciowelista2.LoginForm;
import org.ts.techsieciowelista2.Repositories.UserRepository;
import org.ts.techsieciowelista2.User;

import java.util.Date;

/**
 * Login service
 */
@Service
public class LoginService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.token.key}")
    private String key;

    /**
     * @param passwordEncoder
     * @param userRepository
     */
    // konstruktor z Autowired
    @Autowired
    public LoginService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * @param loginForm
     * @return token
     */
    public String userLogin(LoginForm loginForm){
        String login = loginForm.getLogin();
        System.out.println("Login attempt for user: " + login);
        //String password = loginForm.getPassword();
        String hashPassword = userRepository.findHashedPasswordByUsername(login);
        //tutaj pobrać dane uzytkownika z bazy do porównania
        if(passwordEncoder.matches(loginForm.getPassword(), hashPassword)){
            long timeMillis = System.currentTimeMillis();
            User username = userRepository.findByUsername(login);
            String token = Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis + 60 * 60 * 1000))
                    .claim("id", username)
                    .claim("role", username.getRole())
                    .signWith(SignatureAlgorithm.HS256,key)
                    .compact();
            return token;
        }else{
            return null;
        }}
}
