<#macro main title>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>${title} | ТехноМир</title>
        <style>
            /* те же стили, что и на главной */
            body { font-family: Arial; margin: 0; padding: 0; }
            .navbar { background: #333; padding: 10px 20px; }
            .navbar a { color: white; margin-right: 15px; text-decoration: none; }
            .navbar-right { float: right; }
            .container { padding: 20px; }
            .products-grid { display: flex; flex-wrap: wrap; gap: 20px; }
            .product-card { border: 1px solid #ddd; padding: 15px; width: 200px; }
            .filters { margin-bottom: 20px; padding: 10px; background: #f9f9f9; }
            .filter-group { margin: 5px 0; }
        </style>
    </head>
    <body>
    <div class="navbar">
        <a href="/">Главная</a>
        <a href="/catalog">Каталог</a>
        <a href="/cart">Корзина (<#if cartTotalItems??>${cartTotalItems}<#else>0</#if>)</a>
        <div class="navbar-right">
            <#if isAuthenticated>
                <span style="color:white;">Привет, ${username!}!</span>
                <a href="/logout">Выйти</a>
            <#else>
                <a href="/login">Вход</a>
                <a href="/register">Регистрация</a>
            </#if>
        </div>
    </div>
    <div class="container">
        <#nested>
    </div>
    </body>
    </html>
</#macro>