<%-- 
    Document   : header
    Created on : Apr 19, 2015, 9:01:05 PM
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
                    <div class="left"><img src="resources/images/logo.png" alt="devTALK Logo"/></div>
                    <c:if test="${sessionScope.User!= null}">
                        <div class="right" id="logoutLink"><a href="security?action=logout">Log Out</a></div>
                    </c:if>
                </div>
            </div>