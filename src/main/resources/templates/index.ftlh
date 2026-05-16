<#import "/spring.ftl" as spring>
<#include "_macros.ftlh">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "index.title"/></title>
    <style>
        body { font-family: Arial; max-width: 800px; margin: 100px auto; text-align: center; }
        .buttons a { display: inline-block; margin: 10px; padding: 10px 20px; background: #0066cc; color: white; text-decoration: none; border-radius: 5px; }
        .buttons a:hover { background: #0052a3; }
        .user-info { background: #e6f7ff; padding: 15px; border-radius: 10px; }
    </style>
</head>
<body>
<h1><@spring.message "index.heading"/></h1>

<#if isAuthenticated>
    <div class="user-info">
        <p><@spring.message code="index.greeting" args=[username]/></p>
        <div class="buttons">
            <a href="/feed"><@spring.message "index.link.feed"/></a>
            <a href="/users"><@spring.message "index.link.users"/></a>
            <a href="/logout"><@spring.message "index.link.logout"/></a>
        </div>
    </div>
<#else>
    <div class="user-info">
        <p><@spring.message "index.not.authenticated"/></p>
        <div class="buttons">
            <a href="/login"><@spring.message "index.link.login"/></a>
            <a href="/register"><@spring.message "index.link.register"/></a>
        </div>
    </div>
</#if>
<@footer/>
</body>
</html>
