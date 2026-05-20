<#import "layout.ftlh" as layout>
<@layout.main title="Корзина">
    <h1>Корзина</h1>

    <#if cart.items?has_content>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr><th>Товар</th><th>Цена</th><th>Количество</th><th>Сумма</th><th></th></tr>
            </thead>
            <tbody>
            <#list cart.items as productId, item>
                <tr>
                    <td>${item.name}</td>
                    <td>${item.price} руб.</td>
                    <td>
                        <form method="post" action="/cart/update" style="display:flex; gap:5px;">
                            <input type="hidden" name="productId" value="${item.productId}">
                            <input type="number" name="quantity" value="${item.quantity}" min="0" style="width:60px;">
                            <button type="submit">Обновить</button>
                        </form>
                    </td>
                    <td>${item.totalPrice} руб.</td>
                    <td>
                        <form method="post" action="/cart/remove/${item.productId}">
                            <button type="submit" onclick="return confirm('Удалить товар?')">Удалить</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
            <tfoot>
            <tr><td colspan="3" align="right"><strong>Итого:</strong></td><td><strong>${cart.totalPrice} руб.</strong></td><td></td></tr>
            </tfoot>
        </table>

        <div style="margin-top:20px;">
            <form method="post" action="/cart/clear" style="display:inline;">
                <button type="submit" onclick="return confirm('Очистить корзину?')">Очистить корзину</button>
            </form>
            <a href="/catalog" style="margin-left:20px;">Продолжить покупки</a>
            <a href="/checkout" style="margin-left:20px; background:#4CAF50; color:white; padding:8px 15px; text-decoration:none;">Оформить заказ</a>
        </div>
    <#else>
        <p>Корзина пуста. <a href="/catalog">Перейти в каталог</a></p>
    </#if>

</@layout.main>