<%-- 
    Document   : adminAddEdit
    Created on : Apr 20, 2015, 5:45:11 PM
    Author     : smckee
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="header.jsp" %>
<%@include file="menu.jsp" %>

            <div id="content">
                <div class="centerAlignDiv">
                    <h1>${sessionScope.User.getPrimaryRole()} : ${sessionScope.task}</h1>
                </div>
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>
                    <c:if test="${error == null && param.projectDesc != null}">
                        <div>${param.projectDesc} added!</div>
                    </c:if>

                    <c:choose>
                        <c:when test="${error == null}">
                            <c:set var="projectDesc" scope="page" value=""/>
                            <c:set var="active"    scope="page" value=""/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="projectDesc" scope="page" value="${param.projectDesc}"/>
                            <c:set var="active"      scope="page" value="${param.active}"/>
                        </c:otherwise>
                    </c:choose>

                    <form action="projCre8" method="post" name="admin">
                        <div style="width: 500px;" id="fieldset" class="centerAlignDiv">
                            <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                                <span class="required">*</span> denotes a required field
                            </div>

                            <fieldset style="width: 100%; margin-top: -5px">
                                <legend>Create/Edit Project</legend>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="projectDesc" class="bold em7">
                                        Project Name:
                                    </label>
                                    <input type="text" name="projectDesc" id="projectDesc" size="32" maxlength="45" value="${pageScope.projectDesc}" aria-required="true" required />
                                </div>
                                <%-- TODO: add in drop down for Project Lead selection --%>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="active" class="bold em7">
                                        Project Active:
                                    </label>
                                    <c:set var="checked" value="" scope="request"/>
                                    <c:if test="${empty pageScope.active || pageScope.active == 'Y'}">
                                        <c:set var="checked" value="checked" scope="request"/>
                                    </c:if>
                                    <input type="radio" name="active" id="activeY" value="Y" aria-required="true" <c:out value="${checked}"/>> Yes
                                    <input type="radio" name="active" id="activeN" value="Y" aria-required="true" /> No
                                </div>
                                <div style="padding:2%" id="formButtons">
                                    <input type="submit" value="Add" />
                                    <span style="padding-left:5%; margin-left:5%">
                                        <input type="reset" value="Clear" title="Clear" />
                                    </span>
                                </div>
                            </fieldset>
                        </div>
                    </form>
                </div>
            </div>

<%@include file="footer.jsp" %>
