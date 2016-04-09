<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href='${pageContext.request.contextPath}/resource/main.css' rel="stylesheet" type="text/css">
    <title>Traffic Threat</title>


</head>

<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        Add Image
        <button class="btn btn-default goToMainPage" onclick="window.location.href='/TrafficThreat'">Go to main page
        </button>
    </div>
    <div class="panel-body">
        <%@include file="partOfPage/buttons/loginRegistrationButton.jsp" %>

        <button class='btn btnMenu btn-primary' onclick="location.href='showThreats'">Show threats</button>

        <sec:authorize access="hasAnyRole('ADMIN', 'USER')">
            <button class='btn btnMenu btn-primary' onclick="location.href='user/addThreat'">Add threat</button>
            <button class='btn btnMenu btn-primary' onclick="location.href='user/addVoteForThreat'">Add vote</button>
            <button class='btn btnMenu btn-primary' onclick="location.href='user/addImage'">Add image</button>
            <button class='btn btnMenu btn-primary' onclick="location.href='showLogs'">Show logs</button>
        </sec:authorize>

        <sec:authorize access="hasRole('ADMIN')">
            <button class='btn btnMenu btn-primary' onclick="location.href='admin/showUsers'">Show users</button>
        </sec:authorize>
    </div>
</div>
</body>
</html>