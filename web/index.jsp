<%-- 
    Document   : index
    Created on : 18-Nov-2019, 14:09:10
    Author     : HaAnh
--%>

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
        
        <h2>List Books</h2>
        <form action="searchBook">
            <input type="text" name="searchedTitle" value="${param.searchedTitle}" placeholder="Search Book Title" />
            <input type="text" name="minMoney" value="${param.minMoney}" placeholder="Min Price"/>
            <input type="text" name="maxMoney" value="${param.maxMoney}" placeholder="Max Price" />
            <select name="category">
                <option value="0">All</option>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.key}">${category.value}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Search" />
        </form>


        <c:if test="${not empty books}">
            <c:forEach var="book" items="${books}">
                <div class="product col my-1-of-4">
                    <img src="${book.image}"/>
                    <div>
                        <h3>${book.title}</h3>
                        <b>Author: </b> ${authors[book.authorId]}<br/>
                        <b>Price: </b> $${book.price}<br/>
                    </div>
                    <form action="addToCart" method="POST">
                        <input type="hidden" name="bookId" value="${book.id}" />
                        <input type="hidden" name="searchedTitle" value="${param.searchedTitle}"/>
                        <input type="hidden" name="minMoney" value="${param.minMoney}"/>
                        <input type="hidden" name="maxMoney" value="${param.maxMoney}"/>
                        <input type="hidden" name="category" value="0"/>
                        <input type="submit" value="Add to Cart" />
                    </form>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty books}">
            <div class="message">No  books found!</div>
        </c:if>
    </body>
</html>
