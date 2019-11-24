<%-- 
    Document   : admin-index
    Created on : 21-Nov-2019, 11:24:06
    Author     : HaAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/myStyle.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2>List all Books</h2>
        
        <c:if test="${not empty books}">
            <table border="0">
                <thead>
                <th>No.</th>
                <th>Image</th>
                <th>Title</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Active</th>
                <th>Delete</th>
                <th>View Detail</th>
            </thead>
            <c:forEach items="${books}" var="book" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>
                        <c:url var="imgLink" value="/${book.image}"></c:url>
                        
                        <%--<c:url var="imgLink" value="${book.image}"></c:url>--%>
                         <!--http://localhost:8084/J3LP0002BookStore/admin/1.PNG-->
                        <c:if test="${empty book.image}">
                            (No Photo)
                        </c:if>
                        <c:if test="${not empty book.image}">
                            <img style="width: 120px;" src="${imgLink}"/>
                        </c:if>
                    </td>
                    <td>${book.title}</td>
                    <td>${book.price}</td>
                    <td>123</td>
                    <td>
                        <c:if test="${book.active}">
                            <span class="active">
                                Active
                            </span>
                        </c:if>
                        <c:if test="${book.active eq false}">
                            <span class="inactive">
                                Inactive
                            </span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${book.active}">
                            <c:url var="deleteLink" value="/admin/deleteBook"></c:url>
                            <!--<a class="delete-link" href="${deleteLink}">Delete</a>-->
                            <form action="${deleteLink}" method="POST">
                                <input type="hidden" name="bookId" value="${book.id}" />
                                <input type="submit" value="Delete" onclick="return confirm('Do you want to delete?')"/>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:url var="viewDetailLink" value="/admin/viewBookDetail">
                            <c:param name="bookId" value="${book.id}"/>
                        </c:url>
                        <a href="${viewDetailLink}">View Detail</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
