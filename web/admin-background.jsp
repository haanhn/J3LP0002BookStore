<%-- 
    Document   : admin-background
    Created on : 21-Nov-2019, 11:37:21
    Author     : HaAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:url var="cssLink" value="/css/myStyle.css">
        </c:url>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
        <link href="${cssLink}" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h1>Book Store: Admin Page</h1>
        <div class="box-welcome">
            Welcome, ${currentUser.fullname}
        </div>

        <ul class="list-function">
            <li>
                <!--c:url: get contextPath + url-->
                <!--a: get serverPath + url-->
                <c:url var="getAllBooksLink" value="/admin/getAllBooks">
                </c:url>
                <a href="${getAllBooksLink}">Get all Books</a>
            </li>
            
            <li>
                <c:url var="pageInsertBookLink" value="/admin/pageInsertBook">
                </c:url>
                <a href="${pageInsertBookLink}">Insert Book</a>
            </li>
            
            <c:if test="${currentUser.roleId eq 'AD001'}">
                <li>
                    <c:url var="pageInsertUserLink" value="ServletCenter">
                        <c:param name="action" value="PageInsert"/>
                    </c:url>
                    <a href="${pageInsertUserLink}">Insert new User</a>
                </li>
                <li>
                    <c:url var="viewPromoLink" value="ServletCenter">
                        <c:param name="action" value="ViewPromotions"/>
                    </c:url>
                    <a href="${viewPromoLink}">View Promotions</a>
                </li>
                <li>
                    <c:url var="pageInsertPromoLink" value="ServletCenter">
                        <c:param name="action" value="PageInsertPromotion"/>
                    </c:url>
                    <a href="${pageInsertPromoLink}">Insert new Promotion</a>
                </li>
            </c:if>
        </ul>

        <%= request.getRequestURI() %>
        <c:if test="${not empty includedPage}">
            <jsp:include page="${includedPage}"/>
        </c:if>
    </body>
</html>
