package ru.itis.controlworkstub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.controlworkstub.model.MessageEntity;
import ru.itis.controlworkstub.model.UserEntity;
import ru.itis.controlworkstub.repository.MessageRepository;
import ru.itis.controlworkstub.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // Получить текущего залогиненного пользователя
    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Список диалогов: для каждого собеседника – последнее сообщение
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDialogs() {
        UserEntity currentUser = getCurrentUser();
        List<MessageEntity> lastMessages = messageRepository.findLastMessageInEachDialog();

        // Фильтруем только те диалоги, где участвует текущий пользователь
        // и группируем по собеседнику
        Map<Long, Map<String, Object>> dialogMap = new LinkedHashMap<>();

        for (MessageEntity msg : lastMessages) {
            UserEntity sender = msg.getSender();
            UserEntity recipient = msg.getRecipient();

            // Определяем собеседника (не текущего пользователя)
            UserEntity interlocutor = sender.equals(currentUser) ? recipient : sender;
            if (!interlocutor.equals(currentUser)) { // исключаем диалог с самим собой
                if (!dialogMap.containsKey(interlocutor.getId())) {
                    Map<String, Object> dialog = new HashMap<>();
                    dialog.put("interlocutor", interlocutor);
                    dialog.put("lastMessage", msg);
                    dialogMap.put(interlocutor.getId(), dialog);
                }
            }
        }
        return new ArrayList<>(dialogMap.values());
    }

    // Полная переписка между текущим пользователем и другим пользователем
    @Transactional(readOnly = true)
    public List<MessageEntity> getConversationWith(Long otherUserId) {
        UserEntity currentUser = getCurrentUser();
        UserEntity otherUser = userRepository.findById(otherUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return messageRepository.findConversation(currentUser, otherUser);
    }

    // Отправить сообщение
    @Transactional
    public MessageEntity sendMessage(Long recipientId, String text) {
        UserEntity sender = getCurrentUser();
        UserEntity recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found"));
        MessageEntity message = MessageEntity.builder()
                .text(text)
                .sender(sender)
                .recipient(recipient)
                .isRead(false)
                .build();
        return messageRepository.save(message);
    }

    // Пометить сообщение как прочитанное
    @Transactional
    public void markAsRead(Long messageId) {
        MessageEntity message = messageRepository.findById(messageId).orElse(null);
        if (message != null && message.getRecipient().equals(getCurrentUser())) {
            message.setRead(true);
            messageRepository.save(message);
        }
    }

    // Получить все сообщения (для админа) с сортировкой по дате DESC
    @Transactional(readOnly = true)
    public List<MessageEntity> getAllMessagesSorted() {
        return messageRepository.findAllByOrderBySentAtDesc();
    }

    // Заблокировать сообщение (заменить текст)
    @Transactional
    public void censorMessage(Long messageId) {
        MessageEntity message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setText("<!CENSORED!>");
        messageRepository.save(message);
    }
}
