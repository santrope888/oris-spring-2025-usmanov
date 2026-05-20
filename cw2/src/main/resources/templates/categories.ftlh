<#import "layout.ftlh" as layout>
<@layout.main title="Каталог товаров">
    <h1>Категории</h1>
    <div class="categories">
        <#list categories as cat>
            <div class="category-card">
                <h2><a href="/catalog/${cat.slug}">${cat.name}</a></h2>
                <p>${cat.description}</p>
            </div>
        </#list>
    </div>
</@layout.main>