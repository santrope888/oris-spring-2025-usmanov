<#import "/spring.ftl" as spring>
<#include "_macros.ftlh">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "register.title"/></title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 400px; margin: 50px auto; }
        input, button { display: block; width: 100%; margin-bottom: 10px; padding: 8px; }
        .error { color: red; background: #ffe6e6; padding: 10px; border-radius: 5px; margin-bottom: 15px; }
    </style>
</head>
<body>
<h2><@spring.message "register.heading"/></h2>

<#if (error??)>
    <div class="error">
        <@spring.message "register.error.duplicate"/>
    </div>
</#if>

<form action="/register" method="post">
    <input type="text" name="username" placeholder="<@spring.message code='register.placeholder.username'/>" required autofocus>
    <input type="password" name="password" placeholder="<@spring.message code='register.placeholder.password'/>" required>
    <button type="submit"><@spring.message "register.button"/></button>
</form>
<p><@spring.message "register.have.account"/> <a href="/login"><@spring.message "register.link.login"/></a></p>
<@footer/>
</body>
</html>
