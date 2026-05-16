<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 400px; margin: 50px auto; }
        input, button { display: block; width: 100%; margin-bottom: 10px; padding: 8px; }
        .error { color: red; background: #ffe6e6; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
    </style>
</head>
<body>
<h2>Регистрация в соцсети</h2>

<#if (error??)>
    <div class="error">
        Ошибка: пользователь с таким именем уже существует. Выберите другое имя.
    </div>
</#if>

<form action="/register" method="post">
    <input type="text" name="username" placeholder="Имя пользователя" required autofocus>
    <input type="password" name="password" placeholder="Пароль" required>
    <button type="submit">Зарегистрироваться</button>
</form>
<p>Уже есть аккаунт? <a href="/login">Войти</a></p>
</body>
</html>