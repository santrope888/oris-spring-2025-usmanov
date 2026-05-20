<!DOCTYPE html>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<h2>Вход в соцсеть</h2>
<form action="/login" method="post">
    <div>
        <label>Имя пользователя:</label>
        <input type="text" name="username" required>
    </div>
    <div>
        <label>Пароль:</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">Войти</button>
</form>
<#if (registered??)>
    <div style="color: green; background: #e6ffe6; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
        Регистрация успешна! Теперь войдите.
    </div>
</#if>
<p>Нет аккаунта? <a href="/register">Зарегистрируйся</a></p>
</body>
</html>