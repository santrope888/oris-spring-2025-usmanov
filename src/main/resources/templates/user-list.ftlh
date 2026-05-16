<#import "/spring.ftl" as spring>
<#include "_macros.ftlh">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "users.title"/></title>
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
<a class="back" href="/"><@spring.message "users.back.home"/></a>
<h1><@spring.message "users.heading"/></h1>

<#if users?has_content>
    <#list users as user>
        <div class="user">
            <span class="username">${user.username}</span>
            <a class="btn" href="/feed/${user.id}"><@spring.message "users.write"/></a>
        </div>
    </#list>
<#else>
    <p><@spring.message "users.empty"/></p>
</#if>
<@footer/>
</body>
</html>
