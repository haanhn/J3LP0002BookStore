<%-- 
    Document   : admin-insert-book
    Created on : 21-Nov-2019, 14:29:13
    Author     : HaAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Insert new Book</h2>
        <c:set var="e" value="${error}"/>
        <c:url var="insertLink" value="/admin/insertBook"></c:url>
         <form action="${insertLink}" method="POST" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <th>Title</th>
                    <td>
                        <input type="text" name="title" value="${params.title}" />
                        <span class="show-input-err">${e.titleErr}</span> 
                    </td>
                </tr>
                <tr>
                    <th>Price</th>
                    <td>
                        <input type="text" name="price" value="${params.price}"/>
                        <span class="show-input-err">${e.priceErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Quantity</th>
                    <td>
                        <input type="text" name="quantity" value="${params.quantity}"/>
                        <span class="show-input-err">${e.quantityErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td>
                        <textarea name="description">${params.description}</textarea>
                        <span class="show-input-err">${e.descriptionErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Author</th>
                    <td>
                        <select name="author">
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.key}">
                                    ${author.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Image</th>
                    <td>
                        <input type="file" name="bookImage"/>
                    </td>
                </tr>
<!--                <tr>
                    <th>Active</th>
                    <td>
                        <input type="checkbox" name="active" value="" checked="checked" />
                    </td>
                </tr>-->
                <tr>
                    <th>Category</th>
                    <td>
                        <select name="category">
                            <c:forEach items="${categories}" var="category">
                                <option value="${category.key}">
                                    ${category.value}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Insert Book" />
                    </td>
                </tr>
            </table>
        </form>
        <div class="message">${messagePhoto}</div>
        <div class="message">${messageBook}</div>
    </body>
</html>
