package com.nnk.springboot.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.nnk.springboot.domain.User;

public class UserRepositoryIT extends AbstractRepositoryIT<User, Integer> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected User createEntity() {
        return new User(null, "John", passwordEncoder.encode("John@678"), "Full John", "USER");
    }

    @Override
    protected List<User> getMoreEntities() {
        return List.of(
            new User(null, "Jane", passwordEncoder.encode("Jane@678"), "Full Jane", "ADMIN"),
            new User(null, "Other", passwordEncoder.encode("Other@78"), "Full Other", "USER")
        );
    }

    @Override
    protected JpaRepository<User, Integer> getRepository() {
        return userRepository;
    }

    @Override
    protected Integer getId(User user) {
        return user.getId();
    }

    @Override
    protected void verifyCreate(User user) {
        assertThat(user.getUsername()).isEqualTo("John");
        assertThat(passwordEncoder.matches("John@678", user.getPassword())).isTrue();
        assertThat(user.getFullname()).isEqualTo("Full John");
        assertThat(user.getRole()).isEqualTo("USER");
    }

    @Override
    protected void updateEntity(User user) {
        user.setUsername("Johnny");
        user.setPassword(passwordEncoder.encode("Johnny@8"));
        user.setFullname("Full Johnny");
        user.setRole("ADMIN");
    }

    @Override
    protected void verifyUpdate(User user) {
        assertThat(user.getUsername()).isEqualTo("Johnny");
        assertThat(passwordEncoder.matches("Johnny@8", user.getPassword())).isTrue();
        assertThat(user.getFullname()).isEqualTo("Full Johnny");
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }
}
