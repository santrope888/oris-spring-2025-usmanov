<#import "/spring.ftl" as spring>
<#include "_macros.ftlh">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "feed.title"/></title>
    <style>
        body { font-family: Arial; max-width: 600px; margin: 40px auto; }
        .dialog { border: 1px solid #ccc; padding: 10px; margin-bottom: 10px; border-radius: 8px; }
        .dialog h3 { margin: 0 0 5px; }
        .last-message { color: #555; font-size: 0.9em; }
        .timestamp { font-size: 0.8em; color: #999; }
        a { text-decoration: none; color: #0066cc; }
        .logout { float: right; }
        .back { margin-bottom: 15px; display: inline-block; }
    </style>
</head>
<body>
<a class="back" href="/"><@spring.message "feed.back.home"/></a>
<div class="logout">
    <a href="/logout"><@spring.message "feed.logout"/></a>
</div>
<h1><@spring.message "feed.heading"/></h1>

<#if dialogs?has_content>
    <#list dialogs as dialog>
        <div class="dialog">
            <h3>
                <a href="/feed/${dialog.interlocutor.id}">
                    ${dialog.interlocutor.username}
                </a>
            </h3>
            <div class="last-message">
                ${(dialog.lastMessage.text?length > 70)?then(dialog.lastMessage.text?substring(0,70) + "...", dialog.lastMessage.text)}
            </div>
            <div class="timestamp">
                <@formatDateTime dialog.lastMessage.sentAt/>
            </div>
            <div>
                <#if !dialog.lastMessage.read && dialog.lastMessage.recipient.username == currentUsername>
                    <span style="color: red;"><@spring.message "feed.new"/></span>
                </#if>
            </div>
        </div>
    </#list>
<#else>
    <p><@spring.message "feed.empty"/></p>
</#if>
<@footer/>
</body>
</html>
