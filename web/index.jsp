<%-- 
    Document   : index
    Created on : Apr 19, 2015, 9:43:01 AM
    Author     : smckee
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <title>devTALK</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link type="text/css" rel="stylesheet" href="resources/css/default.css">
    </head>
    <body>
        <div id="wrapper">
            <div id="banner">
                <div id="banner_title">
                    devTALK
                </div>
            </div>
            <div id="content">
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>

                    <div>
                        <form action="security" method="post" name="login">
                            <label>User Name:<input type="text" name="userName" maxlength="16"/></label><br/>
                            <label>Password:<input type="text" name="password" maxlength="16"/></label><br/>
                            <input type="submit" value="Log in"/>
                            <input type="hidden" name="action" value="login"/>
                        </form>
                    </div>
                </div>
            </div>
            <div id="footer">
                devTALK Copyright &copy; 2015 Shawn McKee All Rights Reserved
            </div>
        </div>
    </body>
</html>
