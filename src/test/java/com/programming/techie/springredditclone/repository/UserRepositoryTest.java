package com.programming.techie.springredditclone.repository;

import com.programming.techie.springredditclone.BaseTest;
import com.programming.techie.springredditclone.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByUsername() {
        User user = User.builder()
                .username("TestUser")
                .email("test@example.com")
                .password("password")
                .created(Instant.now())
                .enabled(true)
                .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("TestUser");


        assertTrue(foundUser.isPresent());
        assertEquals("TestUser", foundUser.get().getUsername());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

}
