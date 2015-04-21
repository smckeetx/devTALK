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

                    <form action="/devTALK10/task/adminCre8" method="post" name="admin">
                        <div style="width: 500px;" id="fieldset" class="centerAlignDiv">
                            <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                                <span class="required">*</span> denotes a required field
                            </div>

                            <fieldset style="width: 100%; margin-top: -5px">
                                <legend>Create/Edit Admin</legend>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="firstName" class="bold em7">
                                        First Name:
                                    </label>
                                    <input type="text" name="firstName" id="firstName" size="32" maxlength="25" value="" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="lastName" class="bold em7">
                                        Last Name:
                                    </label>
                                    <input type="text" name="lastName" id="lastName" size="32" maxlength="25" value="" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="email" class="bold em7">
                                        eMail:
                                    </label>
                                    <input type="email" name="email" id="email" size="32" maxlength="50" value="" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="userName" class="bold em7">
                                        User Name:
                                    </label>
                                    <input type="userName" name="userName" id="userName" size="32" maxlength="25" value="" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="extension" class="bold em7">
                                        Extension:
                                    </label>
                                    <input type="text" name="extension" id="extension" size="32" maxlength="6" value="" aria-required="true" required />
                                </div>
                                <div style="padding:2%;">
                                    <div id="error1" class="redbold" aria-live="assertive"></div>
                                    <span class="required">*</span>&nbsp;
                                    <label for="active" class="bold em7">
                                        User Active:
                                    </label>
                                    <input type="radio" name="active" id="activeY" value="Y" aria-required="true" checked /> Yes
                                    <input type="radio" name="active" id="activen" value="Y" aria-required="true" /> No
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
