package com.person.controller;

import com.person.dto.LoginRequest;
import com.person.dto.LoginResponse;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.User;
import com.person.exception.UnauthorizedException;
import com.person.repository.UserRepository;
import com.person.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public BaseResponse login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Kullanıcı adı veya şifre hatalı"));
        if (user.getPassword() == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Kullanıcı adı veya şifre hatalı");
        }

        LoginResponse loginResponse = new LoginResponse(
                jwtService.generateToken(user),
                user.getUserName(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName()
        );

        BaseResponse response = new BaseResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Giriş başarılı");
        response.setData(loginResponse);
        return response;
    }
}
