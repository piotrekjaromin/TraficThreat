<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <%--<link href="<c:url value='/static/css/main.css'/>" rel="stylesheet" type="text/css">--%>
    <title>Traffic Threat</title>


</head>

<body>

<%@include file="partOfPage/buttons/loginRegistrationButton.jsp" %>
<sec:authorize access="hasRole('ADMIN')">
    <button class='btn btnMenu btn-primary' onclick="location.href='addThreat'">Add threat</button>
    <button class='btn btnMenu btn-primary' onclick="location.href='addVoteForThreat'">Add vote for threat</button>
    <button class='btn btnMenu btn-primary' onclick="location.href='showThreats'">Show threat</button>
    <button class='btn btnMenu btn-primary' onclick="location.href='showUsers'">Show users</button>
    <button class='btn btnMenu btn-primary' onclick="location.href='addImage'">Add image</button>
    <button class='btn btnMenu btn-primary' onclick="location.href='showLogs'">show logs</button>
</sec:authorize>
</body>
</html>