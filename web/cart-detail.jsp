<%-- 
    Document   : cart-detail
    Created on : 23-Nov-2019, 22:23:18
    Author     : HaAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
        <link href="css/myStyle.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="background.jsp"/>

        <h2>My Cart</h2>
        <c:if test="${not empty cart}">
            <c:set var="totalBill" value="0"/>
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Book Title</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total Price</th>
                        <th>Update Item</th>
                        <th>Remove Item</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cart}" var="item" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${item.value.title}</td>
                    <form action="updateItemInCart" method="POST">
                        <td>
                            <input type="text" name="quantity" value="${item.value.quantity}" />
                            <c:forEach items="${errorQuan}" var="eQuan">
                                
                            <c:if test="${ eQuan.key eq item.key}">
                            <div class="show-input-err">
                                Max quantity: ${eQuan.value}
                            </div>
                            </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            ${item.value.price}
                        </td>
                        <td>
                            <c:set var="totalPrice" value="${item.value.quantity*item.value.price}"/>
                            <c:set var="totalBill" value="${totalBill + totalPrice}"/>
                            ${totalPrice}
                        </td>
                        <td>
                            <input type="hidden" name="bookId" value="${item.key}" />
                            <input type="submit" value="Update" />
                        </td>
                    </form>
                    <td>
                        <form action="removeItemFromCart" method="POST">
                            <input type="hidden" name="bookId" value="${item.key}" />
                            <input type="submit" value="Remove" onclick="return confirm('Do you want to remove book?')" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div><b>Total Bill: ${totalBill}</b></div>
    <form action="checkOutOrder" method="POST">
        <input type="hidden" name="totalBill" value="${totalBill}" />
        <input type="submit" value="Check out Order" />
    </form>
</c:if>
    
<c:if test="${empty cart}">
    <div class="message">You cart is empty</div>
</c:if>
<c:if test="${empty cart}">
    <div class="message">${message}</div>
</c:if>
</body>
</html>
