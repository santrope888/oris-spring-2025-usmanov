<#macro admin title>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Админка - ${title}</title>
        <style>
            body { font-family: Arial; margin: 0; padding: 0; }
            .admin-nav { background: #2c3e50; padding: 10px 20px; color: white; }
            .admin-nav a { color: white; margin-right: 20px; text-decoration: none; }
            .admin-nav a:hover { text-decoration: underline; }
            .admin-container { padding: 20px; }
            .message { padding: 10px; margin-bottom: 15px; border-radius: 4px; }
            .success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
            .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
            table { border-collapse: collapse; width: 100%; }
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background: #f2f2f2; }
            .btn { display: inline-block; padding: 5px 10px; margin: 2px; text-decoration: none; background: #eee; border-radius: 3px; }
            .btn-danger { background: #dc3545; color: white; }
            .btn-primary { background: #007bff; color: white; }
            form { display: inline; }
        </style>
    </head>
    <body>
    <div class="admin-nav">
        <strong>Панель администратора</strong>
        <a href="/admin">Дашборд</a>
        <a href="/admin/categories">Категории</a>
        <a href="/admin/products">Товары</a>
        <a href="/admin/attribute-types">Типы характеристик</a>
        <a href="/">На сайт</a>
    </div>
    <div class="admin-container">
        <#if success??>
            <div class="message success">${success}</div>
        </#if>
        <#if error??>
            <div class="message error">${error}</div>
        </#if>
        <h1>${title}</h1>
        <#nested>
    </div>
    </body>
    </html>
</#macro>