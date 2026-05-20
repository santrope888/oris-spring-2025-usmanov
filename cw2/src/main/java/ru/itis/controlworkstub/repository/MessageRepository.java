package ru.itis.controlworkstub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.controlworkstub.model.MessageEntity;
import ru.itis.controlworkstub.model.UserEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    // 1. Вся переписка между двумя пользователями (туда и обратно)
    @Query("SELECT m FROM MessageEntity m WHERE " +
            "(m.sender = :user1 AND m.recipient = :user2) OR " +
            "(m.sender = :user2 AND m.recipient = :user1) " +
            "ORDER BY m.sentAt ASC")
    List<MessageEntity> findConversation(@Param("user1") UserEntity user1,
                                         @Param("user2") UserEntity user2);

    // последнее сообщение в каждом ДИАЛОГЕ (между двумя пользователями, без учёта направления)
    @Query(value = """
        SELECT * FROM (
            SELECT *,
                   ROW_NUMBER() OVER (
                       PARTITION BY LEAST(sender_id, recipient_id), GREATEST(sender_id, recipient_id)
                       ORDER BY sent_at DESC
                   ) AS rn
            FROM messages
        ) t
        WHERE rn = 1
        """, nativeQuery = true)
    List<MessageEntity> findLastMessageInEachDialog();

    List<MessageEntity> findAllByOrderBySentAtDesc();
}