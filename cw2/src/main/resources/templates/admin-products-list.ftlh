<#import "admin-layout.ftlh" as layout>
<@layout.admin title="Товары">
    <a href="/admin/products/create" class="btn btn-primary">Создать товар</a>
    <table>
        <tr><th>ID</th><th>Название</th><th>Цена</th><th>Категория</th><th>Действия</th></tr>
        <#list products as prod>
            <tr>
                <td>${prod.id}</td>
                <td>${prod.name}</td>
                <td>${prod.price} руб.</td>
                <td>${prod.category.name!}</td>
                <td>
                    <a href="/admin/products/edit/${prod.id}" class="btn">Редактировать</a>
                    <a href="/admin/products/delete/${prod.id}" class="btn btn-danger" onclick="return confirm('Удалить товар?')">Удалить</a>
                </td>
            </tr>
        </#list>
    </table>
</@layout.admin>