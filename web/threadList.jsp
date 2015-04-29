<%-- 
    Document   : threadList
    Created on : Apr 26, 2015, 4:34:57 PM
    Author     : smckee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="header.jsp" %>
<%@include file="menu.jsp" %>

            <div id="content">
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                        <c:set var="postTxt" value="${param.postTxt}"/>
                    </c:if>
                    <div class="datCol">
                        <form action="thrdRead" method="post">
                            <p>
                                <label for="project" class="bold em7">
                                    Project List:
                                </label>
                                <select name="project" id="project" required style="width:17.5em;">
                                    <option value="0">-- Select Project --</option>
                                    <c:forEach items="${requestScope.projects}" var="project">
                                        <option value="${project.projectID}" ${project.projectID == param.project ? 'selected' : ''}>${project.projectDesc}</option>
                                    </c:forEach>
                                </select>
                                <input type="Submit" value="Go"/>
                            </p>
                        </form>
                    </div>
                    <c:if test="${!empty requestScope.threads}">
                        <form action="thrdRead" method="post">
                            <p>
                                <label for="thread" class="bold em7">
                                    Thread List:
                                </label>
                                <select name="thread" id="thread" required style="width:17.5em;">
                                    <option value="0">-- Select Thread --</option>
                                    <c:forEach items="${requestScope.threads}" var="thread">
                                        <option value="${thread.threadID}" ${thread.threadID == param.thread ? 'selected' : ''}>${thread.threadTitle}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="project" value="${param.project}"/>
                                <input type="Submit" value="Go"/>
                            </p>
                        </form>
                    </c:if>
                    <h1>
                        ${sessionScope.thread.threadTitle}
                    </h1>

                    <c:if test="${!empty sessionScope.thread}">
                        <form action="postCre8" method="post">
                            <div class="datCol">
                                <span class="required">*</span>&nbsp;
                                <label for="post" class="bold em7">
                                    Post:
                                </label>
                                <textarea name="postTxt" id="postTxt" required style="width:98%;height:15em;">${postTxt}</textarea>
                            </div>
                            <div style="padding:2%" id="formButtons">
                                <input type="submit" value="Save" />
                            </div>
                        </form>
                    </c:if>

                    <c:forEach items="${requestScope.posts}" var="post">
                        <div class="postTime">
                            <fmt:formatDate type="both" value="${post.postTime}"/> - ${sessionScope.User.getUserFirstName()} ${sessionScope.User.getUserLastName()}
                        </div>
                        <div class="post">
                            ${post.postContent}
                        </div>
                    </c:forEach>
                <div
            <div
<%@include file="footer.jsp" %>
