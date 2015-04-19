<%-- 
    Document   : index
    Created on : Apr 19, 2015, 9:43:01 AM
    Author     : smckee
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                    <div class="left rightAlignText"><a href="security?action=logout">Log Out</a></div>
                </div>
            </div>
            <div id="content">
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>

                    <div>
                        You have logged in ${sessionScope.User.userFirstName}&nbsp;${sessionScope.User.userLastName}
                    </div>
                </div>
            </div>
            <div id="footer">
                devTALK Copyright &copy; 2015 Shawn McKee All Rights Reserved
            </div>
        </div>
    </body>
</html>
