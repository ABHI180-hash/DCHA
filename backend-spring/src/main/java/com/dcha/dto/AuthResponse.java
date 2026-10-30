package com.dcha.dto;

import lombok.*;

@Getter
@Setter
public class AuthResponse {

    private Boolean success;
    private String token;
    private String tokenType;
    private UserDto user;

    public AuthResponse() {}

    public AuthResponse(Boolean success, String token, String tokenType, UserDto user) {
        this.success = success;
        this.token = token;
        this.tokenType = tokenType;
        this.user = user;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }

    public static class AuthResponseBuilder {
        private Boolean success;
        private String token;
        private String tokenType;
        private UserDto user;

        public AuthResponseBuilder success(Boolean success) { this.success = success; return this; }
        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public AuthResponseBuilder user(UserDto user) { this.user = user; return this; }
        public AuthResponse build() { return new AuthResponse(success, token, tokenType, user); }
    }

    @Getter
    @Setter
    public static class UserDto {
        private Long id;
        private String name;
        private String email;
        private String role;
        private String avatar;
        private String bio;

        public UserDto() {}

        public UserDto(Long id, String name, String email, String role, String avatar, String bio) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
            this.avatar = avatar;
            this.bio = bio;
        }

        public static UserDtoBuilder builder() {
            return new UserDtoBuilder();
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getAvatar() { return avatar; }
        public void setAvatar(String avatar) { this.avatar = avatar; }

        public String getBio() { return bio; }
        public void setBio(String bio) { this.bio = bio; }

        public static class UserDtoBuilder {
            private Long id;
            private String name;
            private String email;
            private String role;
            private String avatar;
            private String bio;

            public UserDtoBuilder id(Long id) { this.id = id; return this; }
            public UserDtoBuilder name(String name) { this.name = name; return this; }
            public UserDtoBuilder email(String email) { this.email = email; return this; }
            public UserDtoBuilder role(String role) { this.role = role; return this; }
            public UserDtoBuilder avatar(String avatar) { this.avatar = avatar; return this; }
            public UserDtoBuilder bio(String bio) { this.bio = bio; return this; }
            public UserDto build() { return new UserDto(id, name, email, role, avatar, bio); }
        }
    }
}
