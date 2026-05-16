<#import "/spring.ftl" as spring>
<#include "_macros.ftlh">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "login.title"/></title>
</head>
<body>
<h2><@spring.message "login.heading"/></h2>
<form action="/login" method="post">
    <div>
        <label><@spring.message "login.label.username"/></label>
        <input type="text" name="username" required>
    </div>
    <div>
        <label><@spring.message "login.label.password"/></label>
        <input type="password" name="password" required>
    </div>
    <button type="submit"><@spring.message "login.button"/></button>
</form>
<#if (registered??)>
    <div style="color: green; background: #e6ffe6; padding: 10px; border-radius: 5px; margin-bottom: 15px;">
        <@spring.message "login.registered.success"/>
    </div>
</#if>
<p><@spring.message "login.no.account"/> <a href="/register"><@spring.message "login.link.register"/></a></p>
<@footer/>
</body>
</html>
