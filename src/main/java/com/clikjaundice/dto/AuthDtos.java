package com.clikjaundice.dto;

public class AuthDtos {

    public static class SignupRequest {
        private String username;
        private String password;
        private String fullName;

        public SignupRequest() {}

        public SignupRequest(String username, String password, String fullName) {
            this.username = username;
            this.password = password;
            this.fullName = fullName;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String token;
        private String username;
        private String fullName;
        private String message;

        public AuthResponse() {}

        public AuthResponse(String token, String username, String fullName, String message) {
            this.token = token;
            this.username = username;
            this.fullName = fullName;
            this.message = message;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}