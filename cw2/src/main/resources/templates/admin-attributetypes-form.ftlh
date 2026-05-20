<#import "admin-layout.ftlh" as layout>
<@layout.admin title="${attributeType.id???string('Редактирование','Создание')} типа характеристики">
    <form method="post" action="/admin/attribute-types/save">
        <#if attributeType.id??>
            <input type="hidden" name="id" value="${attributeType.id}">
        </#if>
        <label>Название характеристики (например, "Цвет", "ОЗУ"):</label><br>
        <input type="text" name="name" value="${attributeType.name!}" required><br><br>
        <label>Принадлежит категории:</label><br>
        <select name="category.id" required>
            <#list categories as cat>
                <option value="${cat.id}" <#if attributeType.category?? && attributeType.category.id == cat.id>selected</#if>>${cat.name}</option>
            </#list>
        </select><br><br>
        <button type="submit" class="btn btn-primary">Сохранить</button>
        <a href="/admin/attribute-types" class="btn">Отмена</a>
    </form>
</@layout.admin>