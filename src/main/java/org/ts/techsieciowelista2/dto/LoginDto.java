package org.ts.techsieciowelista2.dto;

public class LoginDto {
    private String token;

    public LoginDto() {

    }

    public LoginDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
