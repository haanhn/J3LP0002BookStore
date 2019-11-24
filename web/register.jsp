<%-- 
    Document   : register
    Created on : 24-Nov-2019, 18:51:59
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
        
        <h2>Register Page</h2>
        <c:set var="e" value="${error}"/>
        
        <form action="register" method="POST">
            <table border="0">
                <tr>
                    <th>User Id</th>
                    <td>
                        <input type="text" name="userId" value="${param.userId}" />
                        <div class="show-input-err">${e.userIdErr}</div> 
                    </td>
                </tr>
                <tr>
                    <th>Password</th>
                    <td>
                        <input type="password" name="password" value=""/>
                        <div class="show-input-err">${e.passwordErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Confirm</th>
                    <td>
                        <input type="password" name="confirm" value="" />
                        <div class="show-input-err">${e.confirmErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Fullname</th>
                    <td>
                        <input type="text" name="fullname" value="${param.fullname}" />
                        <div class="show-input-err">${e.fullnameErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>
                        <input type="text" name="email" value="${param.email}" />
                        <div class="show-input-err">${e.emailErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>
                        <input type="text" name="phone" value="${param.phone}" />
                        <div class="show-input-err">${e.phoneErr}</div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Register" />
                    </td>
                </tr>
            </table>
        </form>
        <div class="message">${message}</div>
    </body>
</html>
