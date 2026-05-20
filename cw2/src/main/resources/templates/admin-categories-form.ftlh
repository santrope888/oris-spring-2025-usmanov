<#import "admin-layout.ftlh" as layout>
<@layout.admin title="${category.id???string('Редактирование','Создание')} категории">
    <form method="post" action="/admin/categories/save">
        <#if category.id??>
            <input type="hidden" name="id" value="${category.id}">
        </#if>
        <label>Название:</label><br>
        <input type="text" name="name" value="${category.name!}" required><br><br>
        <label>Slug (только латиница, цифры, дефис):</label><br>
        <input type="text" name="slug" value="${category.slug!}" required><br><br>
        <label>Описание:</label><br>
        <textarea name="description" rows="4" cols="50">${category.description!}</textarea><br><br>
        <button type="submit" class="btn btn-primary">Сохранить</button>
        <a href="/admin/categories" class="btn">Отмена</a>
    </form>
</@layout.admin>