<%-- 
    Document   : index
    Created on : Apr 19, 2015, 9:43:01 AM
    Author     : smckee
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="WEB-INF/header.jsp" %>
            <div id="content">
                <div class="centerAlignDiv">
                    <c:if test="${error != null}">
                        <div class="error">${error}</div>
                    </c:if>

                    <c:if test="${param.error != null}">
                        <div class="error">${param.error}</div>
                    </c:if>

                    <form action="security" method="post" name="login">
                        <input type="hidden" name="action" value="login"/>
                        <div style="width: 500px;" id="fieldset" class="centerAlignDiv">
                            <div style="text-align: left; padding-top: 20px; padding-left: 10px;">
                                <span class="required">*</span> denotes a required field
                            </div>

                            <fieldset style="width: 100%; margin-top: -5px">
                                <legend>Log In</legend>
                                <div style="padding:2%;">
                                    <label for="userName" class="bold">
                                        Username:
                                    </label>
                                </div>
                                <div style="padding-left:83%;"><span id="error1" class="redbold" aria-live="assertive"></span></div>
                                <div>
                                    <span class="required">*</span>&nbsp;
                                    <input type="text" name="userName" id="userName" size="32" maxlength="16" value="" aria-required="true" required />
                                </div>
                                <div style="padding:2%">
                                    <label for="pw" class="bold">
                                        Password:
                                    </label>
                                </div>
                                <div style="padding-left:83%;"><span id="error2" class="redbold" aria-live="assertive"></span></div>
                                <div>
                                    <span class="required">*</span>&nbsp;
                                    <input type="password" name="password" id="password" size="32" maxlength="20" value="" aria-required="true" required />
                                </div>
                                <div style="padding:2%" id="formButtons">
                                    <input type="submit" value="Login" />
                                    <span style="padding-left:5%; margin-left:5%">
                                        <input type="reset" value="Clear" title="Clear" />
                                    </span>
                                </div>
                            </fieldset>
                        </div>
                    </form>
                </div>
            </div>
<%@include file="WEB-INF/footer.jsp" %>
