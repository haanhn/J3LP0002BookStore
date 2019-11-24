<%-- 
    Document   : login
    Created on : 18-Nov-2019, 15:41:28
    Author     : HaAnh
--%>

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
        
        <h2>Login Page</h2>
        <form action="login" method="POST">
            <table border="0">
                <tr>
                    <td>User Id</td>
                    <td>
                        <input type="text" name="userId" value="${param.userId}" />
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td> <input type="password" name="password" value="" /> </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Login" name="action" />
                    </td>
                </tr>
            </table>
        </form>
        <div class="message">${message}</div>
    </body>
</html>
