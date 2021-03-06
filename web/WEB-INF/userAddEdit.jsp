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
                    <div class="taskHeader">${requestScope.task}</div>
                </div>
                <div class="centerAlignDiv">
                    <c:if test="${requestScope.resetPW != null}">
                        <div class="error">You must reset your password</div>
                    </c:if>
                    <c:if test="${requestScope.error != null}">
                        <div class="error">${requestScope.error}</div>
                    </c:if>
                    <c:if test="${requestScope.error == null && param.firstName != null}">
                        <div>${param.userName}&nbsp;${requestScope.permCode == 'userCre8' || requestScope.permCode == 'adminCre8' ? 'added!':'updated!'}</div>
                    </c:if>

                    <c:choose>
                        <c:when test="${requestScope.error == null  && requestScope.rUser != null}">
                            <c:set var="firstName" scope="page" value="${requestScope.rUser.userFirstName}"/>
                            <c:set var="lastName"  scope="page" value="${requestScope.rUser.userLastName}"/>
                            <c:set var="userName"  scope="page" value="${requestScope.rUser.userName}"/>
                            <c:set var="email"     scope="page" value="${requestScope.rUser.userEmail}"/>
                            <c:set var="userPhone" scope="page" value="${requestScope.rUser.userExtension}"/>
                            <c:set var="active"    scope="page" value="${requestScope.rUser.userActive}"/>
                            <c:set var="permCode"  scope="page" value="${requestScope.permCode}"/>
                            <c:if test="${empty permCode}">
                                <c:set var="permCode"  scope="page" value="${param.permCode}"/>
                            </c:if>
                        </c:when>
                        <c:when test="${requestScope.error == null  && param.firstName == null}">
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
                        <c:otherwise>
                            <c:set var="firstName" scope="page" value="${param.firstName}"/>
                            <c:set var="lastName"  scope="page" value="${param.lastName}"/>
                            <c:set var="userName"  scope="page" value="${param.userName}"/>
                            <c:set var="email"     scope="page" value="${param.email}"/>
                            <c:set var="userPhone" scope="page" value="${param.userPhone}"/>
                            <c:set var="active"    scope="page" value="${param.active}"/>
                            <c:set var="permCode"  scope="page" value="${param.permCode}"/>
                            <c:if test="${empty param.permCode}">
                                <c:set var="permCode"  scope="page" value="${requestScope.permCode}"/>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                        

                    <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                        <span class="required">*</span> denotes a required field
                    </div>
                    <fieldset style="width: 95%;">
                        <legend>Create/Edit</legend>
                        <c:if test="${!empty requestScope.users && requestScope.selfEdit == null}">
                            <div>
                                <form action="userUpdt" method="post">
                                    <div style="height:2em;">
                                        <div class="cL50TxtRgt">
                                            <span class="required">*</span>&nbsp;
                                            <label for="users" class="bold em7">
                                                User List:
                                            </label>
                                        </div>
                                        <div class="cR50TxtLft">
                                            <select name="user" id="user" aria-required="true" required>
                                                <option value="">-- Select User --</option>
                                                <c:forEach items="${requestScope.users}" var="user">
                                                    <option value="${user.userID}" <c:if test="${user.userID == requestScope.rUser.userID}">selected</c:if>>${user.userFirstName}&nbsp;${user.userLastName}&nbsp;-&nbsp;${user.userName}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="submit" value="Go">
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </c:if>

                        <form action="${requestScope.permCode}" method="post" name="admin">
                            <div id="fieldset" class="centerAlignDiv">
                                <input type="hidden" name="userID" value="${requestScope.rUser.userID}">

                                <div style="height:2em;">
                                    <div class="cL50TxtRgt">
                                        <span class="required">*</span>&nbsp;
                                        <label for="firstName" class="bold em7">
                                            First Name:
                                        </label>
                                    </div>
                                    <div class="cR50TxtLft">
                                        <input type="text" name="firstName" id="firstName"  style="width:18em;" maxlength="25" value="${pageScope.firstName}" aria-required="true" required />
                                    </div>
                                </div>
                                <div style="height:2em;">
                                    <div class="cL50TxtRgt">
                                        <span class="required">*</span>&nbsp;
                                        <label for="lastName" class="bold em7">
                                            Last Name:
                                        </label>
                                    </div>
                                    <div class="cR50TxtLft">
                                        <input type="text" name="lastName" id="lastName"  style="width:18em;" maxlength="25" value="${pageScope.lastName}" aria-required="true" required />
                                    </div>
                                </div>
                                <div style="height:2em;">
                                    
                                    <div class="cL50TxtRgt">
                                        <span class="required">*</span>&nbsp;
                                        <label for="email" class="bold em7">
                                            eMail:
                                        </label>
                                    </div>
                                    <div class="cR50TxtLft">
                                        <input type="email" name="email" id="email"  style="width:18em;" maxlength="50" value="${pageScope.email}" aria-required="true" required />
                                    </div>
                                </div>
                                <div style="height:2em;">
                                    
                                    <div class="cL50TxtRgt">
                                        <span class="required">*</span>&nbsp;
                                        <label for="userName" class="bold em7">
                                            User Name:
                                        </label>
                                    </div>
                                    <div class="cR50TxtLft">
                                        <input type="text" name="userName" id="userName"  style="width:18em;" maxlength="25" value="${pageScope.userName}" aria-required="true" required />
                                    </div>
                                </div>
                                <div style="height:2em;">
                                    
                                    <div class="cL50TxtRgt">
                                        <span class="required">*</span>&nbsp;
                                        <label for="userPhone" class="bold em7">
                                            Phone Number:
                                        </label>
                                    </div>
                                    <div class="cR50TxtLft">
                                        <input type="tel" name="userPhone" id="userPhone" style="width:18em;" maxlength="10" value="${pageScope.userPhone}" aria-required="true" min="1000000000" max="9999999999" />
                                    </div>
                                </div>

                                <c:if test="${requestScope.selfEdit != null}">
                                    <div style="height:2em;">

                                        <div class="cL50TxtRgt">
                                            <span class="required">*</span>&nbsp;
                                            <label for="password" class="bold em7">
                                                Password:
                                            </label>
                                        </div>
                                        <div class="cR50TxtLft">
                                            <input type="password" name="password" id="password" style="width:18em;" maxlength="32" value="" aria-required="true"/>
                                        </div>
                                    </div>
                                    <div style="height:2em;">
                                        <div class="cL50TxtRgt">
                                            <span class="required">*</span>&nbsp;
                                            <label for="cpassword" class="bold em7">
                                                Check Password:
                                            </label>
                                        </div>
                                        <div class="cR50TxtLft">
                                            <input type="password" name="cpassword" id="cpassword" style="width:18em;" maxlength="32" value="" aria-required="true"  oninput="check(this)"/>
                                        </div>
                                        <script language='javascript' type='text/javascript'>
                                            function check(input) {
                                                if (input.value !== document.getElementById('password').value) {
                                                    document.getElementById("passwordMistmatch").textContent = 'Password Must be Matching.';
                                                } else {
                                                    // input is valid -- reset the error message
                                                    document.getElementById("passwordMistmatch").textContent = '';
                                                }
                                            }
                                        </script>
                                        <div class="cL50TxtRgt">
                                        </div>
                                        <div class="cR50TxtLft" id="passwordMistmatch">
                                            
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${requestScope.selfEdit == null}">
                                    <div style="height:4.5em;">
                                        <div class="cL50TxtRgt" style="height:4em;">
                                            <span class="required">*</span>&nbsp;
                                            <label for="projects" class="bold em7">
                                                Project List:
                                            </label>
                                        </div>
                                        <div class="cR50TxtLft">
                                            <select name="projects" id="projects" multiple size="3" style="width:18em;">
                                                <option value="0">-- Select Project(s) --</option>
                                                <c:forEach items="${requestScope.projects}" var="project">
                                                    <option value="${project.projectID}" <c:if test="${requestScope.rUser.projectsList.contains(project)}">selected</c:if>>${project.projectDesc}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div style="height:2em;">
                                        
                                        <div class="cL50TxtRgt">
                                            <span class="required">*</span>&nbsp;
                                            <label for="active" class="bold em7">
                                                User Active:
                                            </label>
                                        </div>
                                        <div class="cR50TxtLft">
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
                                    </div>
                                </c:if>
                                <c:if test="${requestScope.selfEdit != null}">
                                    <input type="hidden" name="selfEdit" value="Y"/>
                                </c:if>
                                    <div class="clear"></div>
                                <div style="padding:2%" id="formButtons">
                                    <input type="submit" value="${requestScope.permCode == 'userCre8' || requestScope.permCode == 'adminCre8' ? 'Add' : 'Update'}" />
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
