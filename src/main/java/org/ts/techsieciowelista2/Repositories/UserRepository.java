package org.ts.techsieciowelista2.Repositories;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.User;

import java.util.Date;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("SELECT u.password FROM User u WHERE u.username = :username")
    String findHashedPasswordByUsername(@Param("username") String username);
    User findByUsername(String username);
}

