<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize access="hasAnyRole('ADMIN', 'USER')">
    <%--get user name--%>
    <sec:authentication var="principal" property="principal"/>
    <div class="btn-group">
        <button type="button" class="btn btnMenu btn-primary dropdown-toggle" data-toggle="dropdown">
            Log in as ${principal.username} <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li><a href="/user/userProfile">Show profile</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/logout">Logout</a></li>

        </ul>
    </div>
</sec:authorize>
<sec:authorize access="isAnonymous()">

    <button class='btn btnMenu btn-primary' onclick="location.href='/LibrarySpringMVC/registration'">Sign up</button>
    <button class='btn btnMenu btn-primary' onclick="location.href='/LibrarySpringMVC/login'">Log in</button>


</sec:authorize>