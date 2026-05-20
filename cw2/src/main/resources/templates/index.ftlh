<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Социальная сеть</title>
    <style>
        body { font-family: Arial; max-width: 800px; margin: 100px auto; text-align: center; }
        .buttons a { display: inline-block; margin: 10px; padding: 10px 20px; background: #0066cc; color: white; text-decoration: none; border-radius: 5px; }
        .buttons a:hover { background: #0052a3; }
        .user-info { background: #e6f7ff; padding: 15px; border-radius: 10px; }
    </style>
</head>
<body>
<h1>Добро пожаловать в простую соцсеть!</h1>

<#if isAuthenticated>
    <div class="user-info">
        <p>Привет, <strong>${username}</strong>! Ты уже с нами.</p>
        <div class="buttons">
            <a href="/feed">📬 Мои диалоги</a>
            <a href="/users">👥 Все пользователи</a>
            <a href="/logout">🚪 Выйти</a>
        </div>
    </div>
<#else>
    <div class="user-info">
        <p>Ты не авторизован. Чтобы общаться, войди или зарегистрируйся.</p>
        <div class="buttons">
            <a href="/login">🔐 Войти</a>
            <a href="/register">📝 Регистрация</a>
        </div>
    </div>
</#if>
</body>
</html>
