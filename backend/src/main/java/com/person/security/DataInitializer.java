package com.person.security;

import com.person.entites.User;
import com.person.enums.RecordStatus;
import com.person.enums.Role;
import com.person.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createIfMissing("admin", "Admin123!", Role.ADMIN, "Sistem", "Yöneticisi");
        createIfMissing("user", "User123!", Role.USER, "Standart", "Kullanıcı");
    }

    private void createIfMissing(String username, String rawPassword, Role role, String firstName, String lastName) {
        if (userRepository.findByUserName(username).isPresent()) {
            return;
        }
        User user = new User();
        user.setUserName(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setStatus(RecordStatus.ACTIVE.getValue());
        user.setCreateBy("system");
        user.setCreateDate(new Date());
        userRepository.save(user);
        log.info("Demo kullanıcı oluşturuldu: {} / {}", username, role);
    }
}
