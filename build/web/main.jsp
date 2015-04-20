<%-- 
    Document   : index
    Created on : Apr 19, 2015, 9:43:01 AM
    Author     : smckee
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="header.jsp" %>
<%-- this, and the associated if checks, only exists during development --%>
<c:set var="unsupportedList" value=",1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,"/>
            <div id="nav">
                <c:forEach items="${sessionScope.User.getRolesList()}" var="role">
                    ${role.getRoleDesc()}<br/>
                    <c:forEach items="${role.getPermissionsList()}" var="permission">
                        <c:set var="strng" value=",${permission.getPermissionID()},"/>
                        <c:if test="${fn:contains(unsupportedList, strng)}">
                            <span style="text-decoration: line-through">
                        </c:if>
                            &nbsp;&nbsp;&nbsp;&nbsp;${permission.getPermissionID()}&nbsp;${permission.getPermissionDesc()}<br/>
                        <c:if test="${fn:contains(unsupportedList, strng)}">
                            </span>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </div>
            <div id="content">
                <div class="centerAlignDiv">
                    <h1>${sessionScope.User.getPrimaryRole()}</h1>
                </div>
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>

                    <div>
                        
                    </div>
                </div>
            </div>
<%@include file="footer.jsp" %>
