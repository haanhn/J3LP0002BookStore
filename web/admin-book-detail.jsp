<%-- 
    Document   : admin-book-detail
    Created on : 22-Nov-2019, 15:02:45
    Author     : HaAnh
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Book Detail</h2>

        <c:if test="${empty book.image}">
            (No Photo)
        </c:if>
        <c:if test="${not empty book.image}">
            <c:url var="imgLink" value="/${book.image}"></c:url>
            <img style="width: 200px;" src="${imgLink}"/>
        </c:if>

        <c:set var="e" value="${error}"/>
        <c:url var="updateLink" value="/admin/updateBook"></c:url>
            <form action="${updateLink}" method="POST">
            <table border="0">
                <tr>
                    <th>Id</th>
                    <td>
                        ${book.id}
                        <input type="hidden" name="bookId" value="${book.id}" />
                    </td>
                </tr>
                <tr>
                    <th>Title</th>
                    <td>
                        <input type="text" name="title" value="${book.title}" />
                        <span class="show-input-err">${e.titleErr}</span> 
                    </td>
                </tr>
                <tr>
                    <th>Price</th>
                    <td>
                        <input type="text" name="price" value="${book.price}"/>
                        <span class="show-input-err">${e.priceErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Quantity</th>
                    <td>
                        <input type="text" name="quantity" value="${book.quantity}"/>
                        <span class="show-input-err">${e.quantityErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td>
                        <textarea name="description">${book.description}</textarea>
                        <span class="show-input-err">${e.descriptionErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Imported Date</th>
                    <td>
                        <fmt:formatDate var="dateFormated" value="${book.dateImported}" pattern="dd/MM/yyyy"/>
                        <input type="text" name="dateImported" value="${dateFormated}">
                        <span class="show-input-err">${e.dateImportedErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Active</th>
                    <td>
                        <c:if test="${book.active}">
                            <input type="checkbox" name="active" value="" checked="checked" />
                        </c:if>
                        <c:if test="${book.active eq false}">
                            <input type="checkbox" name="active" value="" />                            
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>Author</th>
                    <td>
                        <select name="author">
                            <c:forEach items="${authors}" var="author">
                                <c:if test="${book.authorId eq author.key}">
                                    <option value="${author.key}" selected="selected">
                                        ${author.value}
                                    </option>
                                </c:if>
                                <c:if test="${book.categoryId ne author.key}">
                                    <option value="${author.key}">
                                        ${author.value}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                </tr>                
                <tr>
                    <th>Category</th>
                    <td>
                        <select name="category">
                            <c:forEach items="${categories}" var="category">
                                <c:if test="${book.categoryId eq category.key}">
                                    <option value="${category.key}" selected="selected">
                                        ${category.value}
                                    </option>
                                </c:if>
                                <c:if test="${book.categoryId ne category.key}">
                                    <option value="${category.key}">
                                        ${category.value}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Update Book" />
                    </td>
                </tr>
            </table>
        </form>
                        
        <c:url var="updatePhotoLink" value="/admin/updateBookImage"></c:url>
        <form action="${updatePhotoLink}" method="POST" enctype="multipart/form-data">
            <input type="file" name="bookImage"/>
            <input type="hidden" name="bookId" value="${book.id}"/>
            <input type="submit" value="Update Photo" />
        </form>
        <div class="message">${message}</div>
        <div class="message">${messageBook}</div>
        <div class="message">${messagePhoto}</div>
    </body>
</html>
