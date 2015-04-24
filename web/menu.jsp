<%-- 
    Document   : menu
    Created on : Apr 20, 2015, 8:43:11 AM
    Author     : smckee
--%>

<%-- this, and the associated if checks, only exists during development --%>
<c:set var="unsupportedList" value=",2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,"/>
            <nav>
                <div id="menu" aria-role="nav">
                    <div class="pure-menu pure-menu-open">
                        <ul class="pure-menu-children">
                            <c:forEach items="${sessionScope.User.getRolesList()}" var="role">
                                <li>
                                    ${role.getRoleDesc()}
                                </li>
                                <c:forEach items="${role.getPermissionsList()}" var="permission">
                                    <c:set var="strng" value=",${permission.getPermissionID()},"/>
                                    <li class="s4">
                                        <c:if test="${fn:contains(unsupportedList, strng)}">
                                            <span style="text-decoration: line-through">
                                        </c:if>
                                        <a href="${permission.getPermissionCode()}">${permission.getPermissionDesc()}</a>
                                        <c:if test="${fn:contains(unsupportedList, strng)}">
                                            </span>
                                        </c:if>
                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </nav>
                                