<%-- 
    Document   : userAddEdit
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

                    <form action="userCre8" method="post" name="admin">
                        <div style="width: 500px;" id="fieldset" class="centerAlignDiv">
                            <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                                <span class="required">*</span> denotes a required field
                            </div>

                            <fieldset style="width: 100%; margin-top: -5px">
                                <legend>Create/Edit</legend>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="firstName" class="bold em7">
                                        First Name:
                                    </label>
                                    <input type="text" name="firstName" id="firstName" size="32" maxlength="25" value="${pageScope.firstName}" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="lastName" class="bold em7">
                                        Last Name:
                                    </label>
                                    <input type="text" name="lastName" id="lastName" size="32" maxlength="25" value="${pageScope.lastName}" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="email" class="bold em7">
                                        eMail:
                                    </label>
                                    <input type="email" name="email" id="email" size="32" maxlength="50" value="${pageScope.email}" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="userName" class="bold em7">
                                        User Name:
                                    </label>
                                    <input type="userName" name="userName" id="userName" size="32" maxlength="25" value="${pageScope.userName}" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="extension" class="bold em7">
                                        Extension:
                                    </label>
                                    <input type="text" name="extension" id="extension" size="32" maxlength="6" value="${pageScope.extension}" aria-required="true" required />
                                </div>
                                <div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="projects" class="bold em7">
                                        Project List:
                                    </label>
                                    <select name="projects" id="projects" multiple="true" required="" size="3" style="width:17.5em;">
                                        <option value="">-- Select Project(s) --</option>
                                        <c:forEach items="${requestScope.projects}" var="project">
                                            <option value="${project.projectID}">${project.projectDesc}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="active" class="bold em7">
                                        User Active:
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
