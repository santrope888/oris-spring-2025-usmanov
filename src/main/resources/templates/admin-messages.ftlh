<#import "/spring.ftl" as spring>
<#include "_macros.ftlh">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><@spring.message "admin.title"/></title>
    <style>
        body { font-family: Arial; max-width: 1000px; margin: 20px auto; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; vertical-align: top; }
        th { background: #f2f2f2; }
        .censored { background: #ffe6e6; }
        .btn { background: #cc0000; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer; }
        .btn:hover { background: #990000; }
        .back { margin-bottom: 15px; display: inline-block; }
    </style>
</head>
<body>
    <a class="back" href="/"><@spring.message "admin.back.home"/></a>
    <h1><@spring.message "admin.heading"/></h1>

    <#if messages?has_content>
        <table>
            <thead>
                <tr>
                    <th><@spring.message "admin.table.id"/></th>
                    <th><@spring.message "admin.table.sender"/></th>
                    <th><@spring.message "admin.table.recipient"/></th>
                    <th><@spring.message "admin.table.text"/></th>
                    <th><@spring.message "admin.table.date"/></th>
                    <th><@spring.message "admin.table.action"/></th>
                </tr>
            </thead>
            <tbody>
                <#list messages as msg>
                    <tr <#if msg.text == "<!CENSORED!>">class="censored"</#if>>
                        <td>${msg.id}</td>
                        <td>${msg.sender.username}</td>
                        <td>${msg.recipient.username}</td>
                        <td>${msg.text}</td>
                        <td><@formatDateTime msg.sentAt/></td>
                        <td>
                            <#if msg.text != "<!CENSORED!>">
                                <form action="/admin/messages/${msg.id}/censor" method="post" style="margin:0">
                                    <button type="submit" class="btn"><@spring.message "admin.censor"/></button>
                                </form>
                            <#else>
                                <span><@spring.message "admin.censored"/></span>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
    <#else>
        <p><@spring.message "admin.empty"/></p>
    </#if>
<@footer/>
</body>
</html>
