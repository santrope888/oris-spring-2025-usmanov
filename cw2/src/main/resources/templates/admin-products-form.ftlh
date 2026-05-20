<#import "admin-layout.ftlh" as layout>
<@layout.admin title="${product.id???string('Редактирование','Создание')} товара">
    <form method="post" action="/admin/products/save">
        <#if product.id??>
            <input type="hidden" name="id" value="${product.id}">
        </#if>

        <label>Название:</label><br>
        <input type="text" name="name" value="${product.name!}" required><br><br>

        <label>Slug:</label><br>
        <input type="text" name="slug" value="${product.slug!}" required><br><br>

        <label>Описание:</label><br>
        <textarea name="description" rows="4" cols="50">${product.description!}</textarea><br><br>

        <label>Цена (руб):</label><br>
        <input type="number" step="0.01" name="price" value="<#if product.price??>${product.price?c}</#if>" required><br><br>

        <label>Категория:</label><br>
        <select name="category.id" required>
            <#list categories as cat>
                <option value="${cat.id}" <#if product.category?? && product.category.id == cat.id>selected</#if>>${cat.name}</option>
            </#list>
        </select><br><br>

        <h3>Характеристики</h3>
        <div id="attributes-container">
            <#if existingValues?? && existingValues?size gt 0>
                <#list existingValues as av>
                    <div class="attr-row">
                        <select name="attrTypeIds">
                            <#list allAttributeTypes as at>
                                <option value="${at.id}" <#if av.attributeType.id == at.id>selected</#if>>${at.name} (${at.category.name})</option>
                            </#list>
                        </select>
                        <input type="text" name="attrValues" value="${av.value}" placeholder="Значение">
                        <button type="button" onclick="this.parentElement.remove()">Удалить</button>
                    </div>
                </#list>
            <#else>
                <div class="attr-row">
                    <select name="attrTypeIds">
                        <#list allAttributeTypes as at>
                            <option value="${at.id}">${at.name} (${at.category.name})</option>
                        </#list>
                    </select>
                    <input type="text" name="attrValues" placeholder="Значение">
                    <button type="button" onclick="this.parentElement.remove()">Удалить</button>
                </div>
            </#if>
        </div>
        <button type="button" onclick="addAttributeRow()">+ Добавить характеристику</button>
        <br><br>

        <button type="submit" class="btn btn-primary">Сохранить товар</button>
        <a href="/admin/products" class="btn">Отмена</a>
    </form>

    <script>
        function addAttributeRow() {
            const container = document.getElementById('attributes-container');
            const div = document.createElement('div');
            div.className = 'attr-row';
            div.innerHTML = `
                <select name="attrTypeIds">
                    <#list allAttributeTypes as at>
                        <option value="${at.id}">${at.name} (${at.category.name})</option>
                    </#list>
                </select>
                <input type="text" name="attrValues" placeholder="Значение">
                <button type="button" onclick="this.parentElement.remove()">Удалить</button>
            `;
            container.appendChild(div);
        }
    </script>
    <style>
        .attr-row { margin-bottom: 8px; }
        .attr-row select, .attr-row input { margin-right: 8px; }
    </style>
</@layout.admin>
