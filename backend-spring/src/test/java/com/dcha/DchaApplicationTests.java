package com.dcha;

import com.dcha.dto.AuthRequest;
import com.dcha.dto.RegisterRequest;
import com.dcha.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DchaApplicationTests {

    @Test
    void testRegisterDto() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Heritage Enthusiast");
        request.setEmail("test@dcha.org");
        request.setPassword("securePassword123");

        assertEquals("Heritage Enthusiast", request.getName());
        assertEquals("test@dcha.org", request.getEmail());
    }

    @Test
    void testAuthRequestDto() {
        AuthRequest request = new AuthRequest();
        request.setEmail("curator@dcha.org");
        request.setPassword("curatorPass");

        assertEquals("curator@dcha.org", request.getEmail());
    }

    @Test
    void testUserEntity() {
        User user = new User();
        user.setId(100L);
        user.setName("Admin User");
        user.setEmail("admin@dcha.org");
        user.setRole("admin");

        assertEquals("admin", user.getRole());
        assertEquals(100L, user.getId());
    }
}
