package ru.itis.controlworkstub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.controlworkstub.service.MessageService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MessageService messageService;

    @GetMapping("/messages")
    public String allMessages(Model model) {
        model.addAttribute("messages", messageService.getAllMessagesSorted());
        return "admin-messages";
    }

    @PostMapping("/messages/{id}/censor")
    public String censorMessage(@PathVariable Long id) {
        messageService.censorMessage(id);
        return "redirect:/admin/messages";
    }
}
