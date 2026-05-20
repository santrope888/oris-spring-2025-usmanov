<#import "admin-layout.ftlh" as layout>
<@layout.admin title="Дашборд">
    <p>Добро пожаловать в админ-панель. Выберите раздел для управления:</p>
    <ul>
        <li><a href="/admin/categories">Управление категориями</a></li>
        <li><a href="/admin/products">Управление товарами</a></li>
        <li><a href="/admin/attribute-types">Управление типами характеристик</a></li>
    </ul>
</@layout.admin>