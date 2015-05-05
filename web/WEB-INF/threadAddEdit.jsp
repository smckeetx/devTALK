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
                    <form action="thrdCre8" method="post" name="admin">
                        <div style="width: 95%;" id="fieldset" class="centerAlignDiv">
                            <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                                <span class="required">*</span> denotes a required field
                            </div>

                            <fieldset style="width: 100%; margin-top: -5px">
                                <legend>Create/Edit</legend>
                               <div class="datCol">
                                    <span class="required">*</span>&nbsp;
                                    <label for="project" class="bold em7">
                                        Project List:
                                    </label>
                                    <select name="project" id="project" required style="width:17.5em;">
                                        <option value="0">-- Select Project --</option>
                                        <c:forEach items="${requestScope.projects}" var="project">
                                            <option value="${project.projectID}">${project.projectDesc}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="datCol">
                                    <span class="required">*</span>&nbsp;
                                    <label for="title" class="bold em7">
                                        Title:
                                    </label>
                                    <input type="text" name="title" id="title" required size="45" maxlength="45"/>
                                </div>

                                <div class="datCol">
                                    <label for="threadType" class="bold em7">
                                        <input type="radio" name="pubPriv" id="threadType" value="public" checked onclick="toggleMenu()">Public
                                        <input type="radio" name="pubPriv" id="threadType" value="private" onclick="toggleMenu()">Private
                                    </label>
                                </div>

                                <div class="datCol" style="display: none" id="participantsDiv">
                                    <label for="participants" class="bold em7">Add Users:</label>
                                    <select name="participants" id="participants" multiple size="3">
                                        <option value="0">-- Select Users --</option>
                                        <c:forEach items="${requestScope.users}" var="user">
                                            <c:if test="${user.userID != sessionScope.User.getUserID()}"> <%-- exclude logged in user from list --%>
                                                <option value="${user.userID}">${user.userFirstName}&nbsp;${user.userLastName}&nbsp;-&nbsp;${user.userName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="datCol">
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
            <script>
                function toggleMenu() {
                    var thisMenu = document.getElementById('participantsDiv');
                    if( thisMenu.style.display == 'block' ) {
                        thisMenu.style.display = 'none';
                    } else { 
                        thisMenu.style.display = 'block';
                    }
                    return thisMenu;
                }
            </script>
<%@include file="footer.jsp" %>
