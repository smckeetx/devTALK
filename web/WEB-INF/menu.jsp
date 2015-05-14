<%-- 
    Document   : menu
    Created on : Apr 20, 2015, 8:43:11 AM
    Author     : smckee
--%>

<%-- this, and the associated if checks, only exists during development --%>
            <nav>
                <div id="menu" aria-role="nav">
                    <div class="pure-menu pure-menu-open">
                        <ul class="pure-menu-children">
                            <c:forEach items="${sessionScope.User.getRolesList()}" var="role">
                                <c:forEach items="${role.getPermissionsList()}" var="permission">
                                    <li class="s4">
                                        <a href="${permission.getPermissionCode()}">${permission.getPermissionDesc()}</a>
                                    </li>
                                </c:forEach>
                            </c:forEach>
                            <li class="s4">
                                <a href="userUpdt?myID=${sessionScope.User.getUserID()}">Edit Account</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
                                