package org.ts.techsieciowelista2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * User entity
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String username;
    private String password;
    private String role;
    private String mail;
    private String fullusername;
    @OneToMany(mappedBy = "userLoan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Loan> userLoanList;
    @OneToMany(mappedBy = "userReview", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Review> userReviewList;

    public List<Review> getUserReviewList() {
        return userReviewList;
    }

    public void setUserReviewList(List<Review> userReviewList) {
        this.userReviewList = userReviewList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFullusername() {
        return fullusername;
    }

    public void setFullusername(String fullusername) {
        this.fullusername = fullusername;
    }

    public List<Loan> getUserLoanList() {
        return userLoanList;
    }

    public void setUserLoanList(List<Loan> userLoanList) {
        this.userLoanList = userLoanList;
    }
}
