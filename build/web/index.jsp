<%-- 
    Document   : index
    Created on : Apr 19, 2015, 9:43:01 AM
    Author     : smckee
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="header.jsp" %>
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
<%@include file="footer.jsp" %>
