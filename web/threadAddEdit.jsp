<%-- 
    Document   : threadAddEdit
    Created on : Apr 26, 2015, 12:05:04 PM
    Author     : smckee
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="header.jsp" %>
<%@include file="menu.jsp" %>

            <div id="content">
                <div class="centerAlignDiv">
                    <h1>${sessionScope.User.getPrimaryRole()} : ${requestScope.task}</h1>
                </div>
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>
                    <c:if test="${error == null && param.firstName != null}">
                        <div>${param.userName} added!</div>
                    </c:if>

                    <c:choose>
                        <c:when test="${error == null}">
                            <c:set var="firstName" scope="page" value=""/>
                            <c:set var="lastName"  scope="page" value=""/>
                            <c:set var="userName"  scope="page" value=""/>
                            <c:set var="email"     scope="page" value=""/>
                            <c:set var="extension" scope="page" value=""/>
                            <c:set var="active"    scope="page" value=""/>
                            <c:set var="permCode"  scope="page" value="${requestScope.permCode}"/>
                            <c:if test="${empty permCode}">
                                <c:set var="permCode"  scope="page" value="${param.permCode}"/>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="firstName" scope="page" value="${param.firstName}"/>
                            <c:set var="lastName"  scope="page" value="${param.lastName}"/>
                            <c:set var="userName"  scope="page" value="${param.userName}"/>
                            <c:set var="email"     scope="page" value="${param.email}"/>
                            <c:set var="extension" scope="page" value="${param.extension}"/>
                            <c:set var="active"    scope="page" value="${param.active}"/>
                            <c:set var="permCode"  scope="page" value="${param.permCode}"/>
                        </c:otherwise>
                    </c:choose>

                    <form action="thrdCre8" method="post" name="admin">
                        <div style="width: 75%;" id="fieldset" class="centerAlignDiv">
                            <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                                <span class="required">*</span> denotes a required field
                            </div>

                            <fieldset style="width: 100%; margin-top: -5px">
                                <legend>Create/Edit</legend>
                               <div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="project" class="bold em7">
                                        Project List:
                                    </label>
                                    <select name="project" id="project" required style="width:17.5em;">
                                        <option value="0">-- Select Project --</option>
                                        <c:forEach items="${sessionScope.User.getProjectsList()}" var="project">
                                            <option value="${project.projectID}">${project.projectDesc}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="title" class="bold em7">
                                        Title
                                    </label>
                                    <input type="text" name="title" id="title" required size="45" maxlength="45"/>
                                </div>

                                <div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="post" class="bold em7">
                                        Post:
                                    </label>
                                    <textarea name="post" id="post" required style="width:98%;height:25em;"></textarea>
                                </div>

                                <div style="padding:2%" id="formButtons">
                                    <input type="submit" value="Add" />
                                    <span style="padding-left:5%; margin-left:5%">
                                        <input type="reset" value="Reset" title="Reset" />
                                    </span>
                                </div>
                                <input type="hidden" name="permCode" value="${pageScope.permCode}"/>
                            </fieldset>
                        </div>
                    </form>
                </div>
            </div>

<%@include file="footer.jsp" %>
