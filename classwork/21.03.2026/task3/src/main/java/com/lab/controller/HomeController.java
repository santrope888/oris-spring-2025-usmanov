package com.lab.controller;

import com.lab.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private VisitRepository visitRepository;

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) userAgent = "Unknown";

        visitRepository.save(userAgent);
        model.addAttribute("visits", visitRepository.findAll());

        return "index";
    }
}
