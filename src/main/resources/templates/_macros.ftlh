<#import "/spring.ftl" as spring>

<#macro formatDateTime dt>
    <#if lang?? && lang == "ru">
        ${dt?string("dd.MM.yyyy HH:mm")}
    <#else>
        ${dt?string("MMM dd, yyyy hh:mm a")}
    </#if>
</#macro>

<#macro footer>
<footer style="margin-top: 40px; text-align: center; color: #666; font-size: 0.9em; border-top: 1px solid #eee; padding-top: 15px;">
    <@spring.message "footer.language"/>: <a href="?lang=ru"><@spring.message "footer.lang.ru"/></a> | <a href="?lang=en"><@spring.message "footer.lang.en"/></a>
</footer>
</#macro>
