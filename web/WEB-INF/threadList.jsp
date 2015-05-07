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
                    <div class="centerAlignDiv">
                        <div class="taskHeader">${requestScope.task}</div>
                    </div>

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
                                    Conversation List:
                                </label>
                                <select name="thread" id="thread" required style="width:17.5em;">
                                    <option value="0">-- Select Conversation --</option>
                                    <c:forEach items="${requestScope.threads}" var="thread">
                                        <option value="${thread.threadID}" ${thread.threadID == param.thread ? 'selected' : ''}>${thread.threadTitle}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="project" value="${param.project}"/>
                                <input type="Submit" value="Go"/>
                            </p>
                        </form>
                    </c:if>
                    <c:if test="${!empty thread}">
                        <form action="thrdUpdt" method="post">
                            <c:if test="${(thread.userID == sessionScope.User.getUserID() || sessionScope.User.getPrimaryRoleCode() != 'user')}">
                                <input type="hidden" name="threadID" value="${thread.threadID}"/>
                                <input type="hidden" name="threadLocked" value="${thread.threadLocked}"/>
                                <c:choose>
                                    <c:when test="${thread.threadLocked}">
                                        <input type="submit" value="Unlock Conversation"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" value="Lock Conversation"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </form>
                    </c:if>
                    <h1>
                        ${sessionScope.thread.threadTitle}
                    </h1>

                    <c:if test="${!empty sessionScope.thread &&
                                  !thread.threadLocked
                                 }">
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
                        <form action="thrdUpdt" method="post">
                            <div class="postTime">
                                <fmt:formatDate type="both" value="${post.postTime}"/> - ${post.getUser().getUserFirstName()}&nbsp;${post.getUser().getUserLastName()}
                            </div>
                            <div class="post">
                                ${post.postContent}
                            </div>
                            <input type="hidden" name="postID" value="${post.postID}"/>
                            <c:if test="${!thread.threadLocked && 
                                          (post.userID == sessionScope.User.getUserID() || 
                                           !sessionScope.User.getPrimaryRoleCode().equals('user')
                                          )
                                         }">
                                    <input type="submit" value="Delete"/>
                            </c:if>
                        </form>
                        <hr class="postHR"/>
                    </c:forEach>
                <div
            <div
<%@include file="footer.jsp" %>
