package ru.itis.controlworkstub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.controlworkstub.model.UserEntity;
import ru.itis.controlworkstub.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public String listUsers(Authentication authentication, Model model) {
        String currentUsername = authentication.getName();
        List<UserEntity> users = userRepository.findAll().stream()
                .filter(u -> !u.getUsername().equals(currentUsername))
                .toList();
        model.addAttribute("users", users);
        return "user-list";
    }
}