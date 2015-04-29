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
                    <c:if test="${requestScope.error != null}">
                        <div class="error">${requestScope.error}</div>
                    </c:if>
                    <c:if test="${requestScope.error == null && param.firstName != null}">
                        <div>${param.userName}&nbsp;${requestScope.permCode == 'userCre8' ? 'added!':'updated!'}</div>
                    </c:if>

                    <c:choose>
                        <c:when test="${requestScope.error == null  && requestScope.user == null}">
                            <c:set var="firstName" scope="page" value=""/>
                            <c:set var="lastName"  scope="page" value=""/>
                            <c:set var="userName"  scope="page" value=""/>
                            <c:set var="email"     scope="page" value=""/>
                            <c:set var="userPhone" scope="page" value=""/>
                            <c:set var="active"    scope="page" value=""/>
                            <c:set var="permCode"  scope="page" value="${requestScope.permCode}"/>
                            <c:if test="${empty permCode}">
                                <c:set var="permCode"  scope="page" value="${param.permCode}"/>
                            </c:if>
                        </c:when>
                        <c:when test="${requestScope.error == null  && requestScope.user != null}">
                            <c:set var="firstName" scope="page" value="${requestScope.user.userFirstName}"/>
                            <c:set var="lastName"  scope="page" value="${requestScope.user.userLastName}"/>
                            <c:set var="userName"  scope="page" value="${requestScope.user.userName}"/>
                            <c:set var="email"     scope="page" value="${requestScope.user.userEmail}"/>
                            <c:set var="userPhone" scope="page" value="${requestScope.user.userExtension}"/>
                            <c:set var="active"    scope="page" value="${requestScope.user.userActive}"/>
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
                            <c:set var="userPhone" scope="page" value="${param.userPhone}"/>
                            <c:set var="active"    scope="page" value="${param.active}"/>
                            <c:set var="permCode"  scope="page" value="${param.permCode}"/>
                        </c:otherwise>
                    </c:choose>
                        

                    <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                        <span class="required">*</span> denotes a required field
                    </div>
                    <fieldset style="width: 95%;">
                        <legend>Create/Edit</legend>
                        <c:if test="${!empty requestScope.users}">
                            <div>
                                <form action="userUpdt" method="post">
                                    <span class="required">*</span>&nbsp;
                                    <label for="users" class="bold em7">
                                        User List:
                                    </label>
                                    <select name="user" id="user" required>
                                        <option value="">-- Select User --</option>
                                        <c:forEach items="${requestScope.users}" var="user">
                                            <option value="${user.userID}">${user.userFirstName}&nbsp;${user.userLastName}&nbsp;-&nbsp;${user.userName}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" value="Go">
                                </form>
                            </div>
                        </c:if>

                        <form action="${requestScope.permCode}" method="post" name="admin">
                            <div style="width: 500px;" id="fieldset" class="centerAlignDiv">
                                <input type="hidden" name="userID" value="${requestScope.user.userID}">

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
                                    <label for="userPhone" class="bold em7">
                                        Phone Number:
                                    </label>
                                    <input type="number" name="userPhone" id="userPhone" style="width:17.5em;" maxlength="10" value="${pageScope.userPhone}" aria-required="true" min="1000000000" max="9999999999" />
                                </div>
                                <div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="projects" class="bold em7">
                                        Project List:
                                    </label>
                                    <select name="projects" id="projects" multiple="true" size="3" style="width:17.5em;">
                                        <option value="0">-- Select Project(s) --</option>
                                        <c:forEach items="${requestScope.projects}" var="project">
                                            <option value="${project.projectID}" <c:if test="${requestScope.user.projectsList.contains(project)}">selected</c:if>>${project.projectDesc}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="active" class="bold em7">
                                        User Active:
                                    </label>

                                    <c:set var="active" value="" scope="request"/>
                                    <c:set var="inactive" value="" scope="request"/>
                                    <c:if test="${empty pageScope.active || pageScope.active == 'Y' || pageScope.active == true}">
                                        <c:set var="activeY" value="checked" scope="page"/>
                                    </c:if>
                                    <c:if test="${!empty pageScope.active && (pageScope.active == 'N' || pageScope.active == false)}">
                                        <c:set var="activeN" value="checked" scope="page"/>
                                    </c:if>

                                    <input type="radio" name="active" id="activeY" value="Y" aria-required="true" <c:out value="${activeY}"/>> Yes
                                    <input type="radio" name="active" id="activeN" value="N" aria-required="true" <c:out value="${activeN}"/> /> No
                                </div>
                                <div style="padding:2%" id="formButtons">
                                    <input type="submit" value="${requestScope.permCode == 'userCre8' ? 'Add' : 'Update'}" />
                                    <span style="padding-left:5%; margin-left:5%">
                                        <input type="reset" value="Reset" title="Reset" />
                                    </span>
                                </div>
                                <input type="hidden" name="permCode" value="${pageScope.permCode}"/>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>

<%@include file="footer.jsp" %>
