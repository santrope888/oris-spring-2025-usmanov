<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Диалог</title>
    <style>
        body { font-family: Arial; max-width: 700px; margin: 20px auto; }
        .message { border-bottom: 1px solid #eee; padding: 8px; }
        .my-message { background: #e6f7ff; }
        .other-message { background: #f9f9f9; }
        .meta { font-size: 0.8em; color: #666; }
        .message-text { margin: 5px 0; }
        form { margin-top: 20px; padding: 10px; background: #f0f0f0; border-radius: 8px; }
        textarea { width: 80%; padding: 8px; }
        button { padding: 8px 16px; }
        .back { margin-bottom: 15px; display: inline-block; }
    </style>
</head>
<body>
<div>
    <a class="back" href="/feed">← Назад к диалогам</a>
    <a class="back" style="float:right" href="/logout">Выйти</a>
</div>
<h2>Диалог с пользователем</h2>
<div id="messages">
    <#if messages?has_content>
        <#list messages as msg>
            <div class="message <#if msg.sender.username == currentUsername>my-message<#else>other-message</#if>">
                <div class="meta">
                    <strong>${msg.sender.username}</strong> · ${msg.sentAt}
                    <#if msg.read> ✓ прочитано</#if>
                </div>
                <div class="message-text">${msg.text}</div>
            </div>
        </#list>
    <#else>
        <p>Сообщений пока нет. Напишите первое сообщение!</p>
    </#if>
</div>

<form action="/feed/${recipientId}" method="post">
    <textarea name="text" rows="3" placeholder="Ваше сообщение..." required></textarea><br>
    <button type="submit">Отправить</button>
</form>
</body>
</html>