<#import "admin-layout.ftlh" as layout>
<@layout.admin title="Типы характеристик">
    <a href="/admin/attribute-types/create" class="btn btn-primary">Создать тип</a>
    <table>
        <tr><th>ID</th><th>Название</th><th>Категория</th><th>Действия</th></tr>
        <#list attributeTypes as at>
            <tr>
                <td>${at.id}</td>
                <td>${at.name}</td>
                <td>${at.category.name!}</td>
                <td>
                    <a href="/admin/attribute-types/edit/${at.id}" class="btn">Редактировать</a>
                    <a href="/admin/attribute-types/delete/${at.id}" class="btn btn-danger" onclick="return confirm('Удалить тип?')">Удалить</a>
                </td>
            </tr>
        </#list>
    </table>
</@layout.admin>