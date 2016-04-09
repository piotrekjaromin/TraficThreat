<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Show users</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>

<div class="panel panel-primary">
    <div class="panel-heading">
        Show users
        <button class="btn btn-default" onclick="window.location.href='/TrafficThreat'">Go to main page</button>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped">
                <tr>
                    <td>uuid</td>
                    <td>login</td>
                    <td>mail</td>
                    <td>name</td>
                    <td>surname</td>
                    <td>threats uuid</td>
                </tr>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.uuid}"/></td>
                        <td><c:out value="${user.login}"/></td>
                        <td><c:out value="${user.mail}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                        <td>
                            <c:forEach items="${user.threats}" var="threat">
                                <c:out value="${threat.uuid}"/><br/>
                            </c:forEach>
                        </td>

                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>


</body>
</html>
