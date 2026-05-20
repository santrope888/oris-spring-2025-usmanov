<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Пользователи</title>
    <style>
        body { font-family: Arial; max-width: 600px; margin: 40px auto; }
        .user { border: 1px solid #ccc; padding: 10px; margin-bottom: 8px; border-radius: 8px; display: flex; justify-content: space-between; align-items: center; }
        .username { font-weight: bold; }
        .btn { background: #0066cc; color: white; padding: 5px 10px; text-decoration: none; border-radius: 4px; }
        .btn:hover { background: #0052a3; }
        .back { margin-bottom: 20px; display: inline-block; }
    </style>
</head>
<body>
<a class="back" href="/">🏠 На главную</a>
<h1>Все пользователи</h1>

<#if users?has_content>
    <#list users as user>
        <div class="user">
            <span class="username">${user.username}</span>
            <a class="btn" href="/feed/${user.id}">💬 Написать</a>
        </div>
    </#list>
<#else>
    <p>Других пользователей пока нет :( Пригласи друзей!</p>
</#if>
</body>
</html>