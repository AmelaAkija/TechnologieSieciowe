package org.ts.techsieciowelista2.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ts.techsieciowelista2.LoginForm;
import org.ts.techsieciowelista2.Repositories.UserRepository;

import java.util.Date;

@Service
public class LoginService {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${jet.token.key}")
    private String key;
    // konstruktor z Autowired
    @Autowired
    public LoginService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public String userLogin(LoginForm loginForm){
        //String hashPassword = loginForm.getPassword();
        //tutaj pobrać dane uzytkownika z bazy do porównania
        if(passwordEncoder.matches(loginForm.getPassword(), "hash hasła z bazy danych")){
            long timeMillis = System.currentTimeMillis();
            String token = Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis+5*60*1000))
                    .claim("id", "id użytkownika z bazy danych")
                    .claim("role", "rola użytkownika z bazy danych")
                    .signWith(SignatureAlgorithm.HS256,key)
                    .compact();
            return token;
        }else{
            return null;
        }


    }
}
