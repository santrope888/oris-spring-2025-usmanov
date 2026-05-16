package ru.itis.controlworkstub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.controlworkstub.service.MessageService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feed")
public class MessageController {

    private final MessageService messageService;

    // Лента диалогов (последнее сообщение в каждом диалоге)
    @GetMapping
    public String feed(Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("dialogs", messageService.getDialogs());
        model.addAttribute("currentUsername", currentUsername);
        return "feed";
    }

    // Страница конкретного диалога с пользователем
    @GetMapping("/{userId}")
    public String dialog(@PathVariable Long userId, Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("messages", messageService.getConversationWith(userId));
        // Передаём ID собеседника, чтобы форма отправки знала, кому писать
        model.addAttribute("recipientId", userId);
        model.addAttribute("currentUsername", currentUsername);
        return "dialog";
    }

    // Отправка сообщения из формы на странице диалога
    @PostMapping("/{userId}")
    public String sendMessage(@PathVariable Long userId,
                              @RequestParam String text) {
        if (text != null && !text.trim().isEmpty()) {
            messageService.sendMessage(userId, text);
        }
        return "redirect:/feed/" + userId;
    }
}
