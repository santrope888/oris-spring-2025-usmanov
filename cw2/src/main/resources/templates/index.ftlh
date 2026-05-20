<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ТехноМир – интернет-магазин электроники</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .navbar { background: #333; overflow: hidden; padding: 10px 20px; }
        .navbar a { color: white; text-decoration: none; padding: 14px 16px; display: inline-block; }
        .navbar a:hover { background: #ddd; color: black; }
        .navbar-right { float: right; }
        .container { padding: 20px; }
        .shop-info { background: #f4f4f4; padding: 15px; margin-bottom: 20px; border-radius: 5px; }
        .address-list { margin-top: 10px; }
        .address-card { background: white; padding: 10px; margin: 5px 0; border-left: 4px solid #4CAF50; }
        .btn { display: inline-block; background: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; margin-top: 10px; }
        .btn:hover { background: #45a049; }
    </style>
</head>
<body>

<div class="navbar">
    <a href="/">Главная</a>
    <a href="/catalog">Каталог</a>
    <a href="/cart">Корзина</a>
    <div class="navbar-right">
        <#if isAuthenticated>
            <span style="color:white;">Привет, ${username}!</span>
            <a href="/logout">Выйти</a>
        <#else>
            <a href="/login">Вход</a>
            <a href="/register">Регистрация</a>
        </#if>
    </div>
</div>

<div class="container">
    <div class="shop-info">
        <h1>ТехноМир</h1>
        <p>Добро пожаловать в интернет-магазин электроники «ТехноМир».
            У нас вы найдёте смартфоны, ноутбуки, комплектующие и аксессуары.
            Быстрая доставка по всей стране.</p>
    </div>

    <h2>Наши магазины</h2>
    <div class="address-list">
        <#list addresses as addr>
            <div class="address-card">
                <strong>${addr.city}</strong><br>
                ${addr.street}<br>
                Телефон: ${addr.phone}
            </div>
        </#list>
    </div>

    <div style="margin-top: 30px;">
        <a href="/catalog" class="btn">Перейти в каталог</a>
        <a href="/cart" class="btn" style="background:#2196F3;">Корзина</a>
    </div>
</div>

</body>
</html>