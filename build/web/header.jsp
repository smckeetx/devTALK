<%-- 
    Document   : header
    Created on : Apr 19, 2015, 9:01:05 PM
    Author     : smckee
--%>
<!DOCTYPE html>
<html>
    <head>
        <title>devTALK</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link type="text/css" rel="stylesheet" href="/devTALK10/resources/css/default.css">
    </head>
    <body>
        <div id="wrapper">
            <div id="banner">
                <div id="banner_title">
                    <div class="left"><img src="/devTALK10/resources/images/logo.png" alt="devTALK Logo"/></div>
                    <c:if test="${sessionScope.User!= null}">
                        <div class="right" id="logoutLink">
                            <a href="/devTALK10/security?action=logout">
                                ${sessionScope.User.userFirstName}&nbsp;${sessionScope.User.userLastName}&nbsp;Log Out
                            </a>
                        </div>
                    </c:if>
                </div>
            </div>