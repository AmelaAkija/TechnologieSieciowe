package org.ts.techsieciowelista2.Repositories;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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
    @Modifying
    @Transactional
    @Query("update User u set u.username = :username, u.password= :password, u.mail =:mail, " +
            "u.fullusername=:fullusername where u.userId = :id")
    void updateUser(@Param(value = "id") int id,@Param(value = "username") String username,
                    @Param(value = "password") String password, @Param(value = "mail") String mail,
                    @Param(value = "fullusername") String fullusername);
}

