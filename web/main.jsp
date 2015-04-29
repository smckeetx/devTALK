<%-- 
    Document   : index
    Created on : Apr 19, 2015, 9:43:01 AM
    Author     : smckee
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="header.jsp" %>
<%@include file="menu.jsp" %>

            <div id="content">
                <div class="centerAlignDiv">
                    <h1>${sessionScope.User.getUserFirstName()}&nbsp;${sessionScope.User.getUserLastName()} : ${sessionScope.User.getPrimaryRole()}</h1>
                </div>
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>

                    <c:if test="${param.error != null}">
                        <div class="error">${param.error}</div>
                    </c:if>

                    <div>
                        
                    </div>
                </div>
            </div>

<%@include file="footer.jsp" %>
