<#import "layout.ftlh" as layout>
<@layout.main title="${category.name}">
    <h1>${category.name}</h1>

    <!-- Блок фильтров -->
    <div class="filters">
        <h3>Фильтры</h3>
        <form method="get" action="/catalog/${category.slug}">
            <#list filterOptions as attrType, values>
                <div class="filter-group">
                    <label>${attrType.name}:</label>
                    <#list values as val>
                        <label>
                            <input type="checkbox" name="attr_${attrType.id}" value="${val}"
                                    <#if selectedFilters[attrType.id?string]?has_content && selectedFilters[attrType.id?string]?seq_contains(val)>
                                        checked
                                    </#if>
                            /> ${val}
                        </label>
                    </#list>
                </div>
            </#list>
            <button type="submit">Применить</button>
            <a href="/catalog/${category.slug}">Сбросить</a>
        </form>
    </div>

    <!-- Сортировка -->
    <div class="sorting">
        <label>Сортировать:</label>
        <select onchange="location.href=this.value">
            <option value="?sort=name,asc" <#if currentSort == "name,asc">selected</#if>>Название (А-Я)</option>
            <option value="?sort=name,desc" <#if currentSort == "name,desc">selected</#if>>Название (Я-А)</option>
            <option value="?sort=price,asc" <#if currentSort == "price,asc">selected</#if>>Цена (сначала дешёвые)</option>
            <option value="?sort=price,desc" <#if currentSort == "price,desc">selected</#if>>Цена (сначала дорогие)</option>
        </select>
    </div>

    <!-- Список товаров -->
    <div class="products-grid">
        <#list products.content as product>
            <div class="product-card">
                <h3><a href="/product/${product.slug}">${product.name}</a></h3>
                <p>${product.description}</p>
                <strong>${product.price} руб.</strong>
                <form method="post" action="/cart/add/${product.id}" style="display:inline;">
                    <input type="number" name="quantity" value="1" min="1" style="width:50px;">
                    <button type="submit">В корзину</button>
                </form>
            </div>
        </#list>
    </div>

    <!-- Пагинация -->
    <div class="pagination">
        <#if products.hasPrevious()>
            <a href="?page=${products.number - 1}&size=${products.size}&sort=${currentSort}">« Назад</a>
        </#if>
        <span>Страница ${products.number + 1} из ${products.totalPages}</span>
        <#if products.hasNext()>
            <a href="?page=${products.number + 1}&size=${products.size}&sort=${currentSort}">Вперед »</a>
        </#if>
    </div>
</@layout.main>