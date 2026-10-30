package com.dcha.config;

import com.dcha.entity.User;
import com.dcha.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Ensure Admin user
        userRepository.findByEmail("admin@dcha.org").ifPresentOrElse(
                admin -> {
                    if (!"admin".equalsIgnoreCase(admin.getRole())) {
                        admin.setRole("admin");
                        userRepository.save(admin);
                    }
                },
                () -> {
                    User admin = User.builder()
                            .name("System Admin")
                            .email("admin@dcha.org")
                            .passwordHash(passwordEncoder.encode("Admin123"))
                            .role("admin")
                            .build();
                    userRepository.save(admin);
                    log.info("Initialized default admin account: admin@dcha.org");
                }
        );

        // Ensure Demo Explorer user
        if (!userRepository.existsByEmail("user@dcha.org")) {
            User demoUser = User.builder()
                    .name("Cultural Explorer")
                    .email("user@dcha.org")
                    .passwordHash(passwordEncoder.encode("Password123"))
                    .role("user")
                    .build();
            userRepository.save(demoUser);
            log.info("Initialized default user account: user@dcha.org");
        }
    }
}
