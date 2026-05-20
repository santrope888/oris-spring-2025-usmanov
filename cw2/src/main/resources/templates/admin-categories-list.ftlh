<#import "admin-layout.ftlh" as layout>
<@layout.admin title="Категории">
    <a href="/admin/categories/create" class="btn btn-primary">Создать категорию</a>
    <table>
        <tr><th>ID</th><th>Название</th><th>Slug</th><th>Действия</th></tr>
        <#list categories as cat>
            <tr>
                <td>${cat.id}</td>
                <td>${cat.name}</td>
                <td>${cat.slug}</td>
                <td>
                    <a href="/admin/categories/edit/${cat.id}" class="btn">Редактировать</a>
                    <a href="/admin/categories/delete/${cat.id}" class="btn btn-danger" onclick="return confirm('Удалить категорию?')">Удалить</a>
                </td>
            </tr>
        </#list>
    </table>
</@layout.admin>