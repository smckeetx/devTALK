<%-- 
    Document   : projectAddEdit
    Created on : Apr 20, 2015, 5:45:11 PM
    Author     : smckee
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="header.jsp" %>
<%@include file="menu.jsp" %>
<c:choose>
    <c:when test="${!empty param.projectDesc}">
        <c:set var="projectDesc" scope="page" value="${param.projectDesc}"/>
        <c:set var="active"      scope="page" value="${param.active}"/>
        <c:set var="pID"         scope="page" value="${param.pID}"/>
    </c:when>
    <c:when test="${!empty requestScope.projectDesc}">
        <c:set var="projectDesc" scope="page" value="${requestScope.projectDesc}"/>
        <c:set var="active"      scope="page" value="${requestScope.active}"/>
        <c:set var="pID"         scope="page" value="${requestScope.pID}"/>
    </c:when>
    <c:otherwise>
        <c:set var="projectDesc" scope="page" value=""/>
        <c:set var="active"      scope="page" value=""/>
    </c:otherwise>
</c:choose>
<c:set var="addUpdate1" value="Add"/>
<c:set var="addUpdate2" value="added"/>
<c:if test="${requestScope.permCode == 'projUpdt'}">
    <c:set var="addUpdate1" value="Update"/>
    <c:set var="addUpdate2" value="updated"/>
</c:if>

            <div id="content">
                <div class="centerAlignDiv">
                    <div class="taskHeader">${requestScope.task}</div>
                </div>
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>
                    <c:if test="${error == null && param.projectDesc != null}">
                        <div>${param.projectDesc}&nbsp;${pageScope.addUpdate2}!</div>
                    </c:if>

                    <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                        <span class="required">*</span> denotes a required field
                    </div>
                    <fieldset style="width: 95%;">
                        <legend>Create/Edit Project</legend>
                        <form action="projUpdt" method="post" name="admin">
                            <div style="width: 500px;" class="centerAlignDiv">
                                <c:if test="${!empty requestScope.projects}">
                                    <div>
                                        <span class="required">*</span>&nbsp;
                                        <label for="projects" class="bold em7">
                                            Project List:
                                        </label>
                                        <select name="projects" id="projects" style="width:17.5em;">
                                            <option value="0">-- Select Project(s) --</option>
                                            <c:forEach items="${requestScope.projects}" var="project">
                                                <option value="${project.projectID}" <c:if test="${requestScope.user.projectsList.contains(project)}">selected</c:if>>${project.projectDesc}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="Submit" value="Go"/>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                        <form action="${requestScope.permCode}" method="post" name="admin">
                            <c:if test="${!empty pageScope.pID}">
                                <input type="hidden" name="pID" value="${pageScope.pID}"/>
                            </c:if>
                            <div style="width: 500px;" class="centerAlignDiv">
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
                                    <c:set var="checkedY" value="" scope="request"/>
                                    <c:set var="checkedN" value="" scope="request"/>
                                    <c:if test="${empty pageScope.active || pageScope.active == 'Y'|| pageScope.active == true}">
                                        <c:set var="checkedY" value="checked" scope="request"/>
                                    </c:if>
                                    <c:if test="${!empty pageScope.active && pageScope.active == 'N'}">
                                        <c:set var="checkedN" value="checked" scope="request"/>
                                    </c:if>
                                    <input type="radio" name="active" id="activeY" value="Y" aria-required="true" <c:out value="${checkedY}"/>> Yes
                                    <input type="radio" name="active" id="activeN" value="N" aria-required="true" <c:out value="${checkedN}"/> /> No
                                </div>
                                <div style="padding:2%" id="formButtons">
                                    <input type="submit" value="${pageScope.addUpdate1}" />
                                    <span style="padding-left:5%; margin-left:5%">
                                        <input type="reset" value="Clear" title="Clear" />
                                    </span>
                                </div>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>

<%@include file="footer.jsp" %>
