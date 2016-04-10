<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show threats</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        function showImage(threatUuid) {
            $.ajax({
                type: "POST",
                url: "showImage",
                dataType: 'text',
                data: {
                    uuid: threatUuid
                },
                success: function (response) {
                    $('#image').html("<img alt='Embedded Image' src='data:image/png;base64," + response + "'/> ");
                },
                error: function (response) {
                    $('#image').html(response);
                }
            });
        }
        ;

        function approve(threatUuid) {
            $.ajax({
                type: "POST",
                url: "admin/approve",
                dataType: 'text',
                data: {
                    uuid: threatUuid
                },
                success: function (response) {
                    location.reload();
                    alert(response)
                },
                error: function (response) {
                    alert("Failure")
                }
            });
        }
        ;

        function disapprove(threatUuid) {
            $.ajax({
                type: "POST",
                url: "admin/disapprove",
                dataType: 'text',
                data: {
                    uuid: threatUuid
                },
                success: function (response) {
                    location.reload();
                    alert(response)
                },
                error: function (response) {
                    alert("Failure")
                }
            });
        }
        ;

        function deleteThreat(threatUuid) {
            $.ajax({
                type: "POST",
                url: "admin/deleteThreat",
                dataType: 'text',
                data: {
                    uuid: threatUuid
                },
                success: function (response) {
                    location.reload();
                    alert(response)
                },
                error: function (response) {
                    alert("Failure")
                }
            });
        }
        ;


    </script>
</head>
<body>
<%--<body onload="insert()">--%>
<div class="panel panel-primary">
    <div class="panel-heading">
        Show threats
        <button class="btn btn-default" onclick="window.location.href='/TrafficThreat'">Go to main page</button>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped">
                <tr>
                    <th>uuid</th>
                    <th>login</th>
                    <th>type</th>
                    <th>description</th>
                    <th>is approved</th>
                    <sec:authorize access="hasRole('ADMIN')">
                        <th>approve</th>
                        <th>delete</th>
                    </sec:authorize>
                    <th>details</th>
                </tr>
                <c:forEach items="${threats}" var="threat">
                    <tr>
                        <td><c:out value="${threat.uuid}"/></td>
                        <td><c:out value="${threat.login}"/></td>
                        <td><c:out value="${threat.type.threatType}"/></td>
                        <td><c:out value="${threat.description}"/></td>
                        <td><c:out value="${threat.isApproved}"/></td>

                        <sec:authorize access="hasRole('ADMIN')">

                            <c:choose>
                                <c:when test="${threat.isApproved eq true}">
                                    <td>
                                        <button class="btn btn-default" onclick="disapprove('${threat.uuid}')">
                                            disapprove
                                        </button>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <button class="btn btn-default" onclick="approve('${threat.uuid}')">approve
                                        </button>
                                    </td>
                                </c:otherwise>
                            </c:choose>

                            <td>
                                <button class="btn btn-default" onclick="deleteThreat('${threat.uuid}')">delete</button>
                            </td>
                        </sec:authorize>
                        <td><button class="btn btn-default"
                                    onclick="location.href='getThreatDetails/?uuid=${threat.uuid}'">
                            details
                        </button></td>
                    </tr>
                </c:forEach>


            </table>
        </div>
        <div id="image"></div>
    </div>
</div>
<%--</textarea>--%>
</body>
</html>
