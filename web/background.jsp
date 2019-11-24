<%-- 
    Document   : background
    Created on : 21-Nov-2019, 11:52:56
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
        <h1><a href="getBooks">Book Store</a></h1>
        <div class="box-welcome">
            Welcome ${currentUser.fullname}
        </div>
        
        <ul class="list-top-navigation">
            <li>
                <c:url var="profileLink" value="ServletCenter">
                    <c:param name="action" value="ViewProfile"/>
                </c:url>
                <a href="${profileLink}">View Profile</a>
            </li>
            <c:if test="${currentUser.roleId ne 'AD001'}">
                <li>
                    <a href="cart-detail.jsp">View Cart</a>
                </li>
            </c:if>
            <c:if test="${not empty currentUser}">
                <li>
                    <c:url var="logOutLink" value="/logOut">
                    </c:url>
                    <a href="${logOutLink}">Log Out</a>
                </li>
            </c:if>
            <c:if test="${empty currentUser}">
                <li>
                    <c:url var="pageLoginLink" value="login.jsp">
                    </c:url>
                    <a href="${pageLoginLink}">Login</a>
                </li>
            </c:if>
        </ul>

        <c:if test="${not empty includedPage}">
            <jsp:include page="${includedPage}"/>
        </c:if>
    </body>
</html>
