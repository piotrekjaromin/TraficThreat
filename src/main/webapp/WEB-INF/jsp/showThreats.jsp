<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                url: "approve",
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
                url: "disapprove",
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
                url: "deleteThreat",
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
                    <th>votes</th>
                    <th>coordinates</th>
                    <th>is approved</th>
                    <th>show image</th>
                    <th>approve</th>
                    <th>delete</th>
                    <th>edit</th>
                </tr>
                <c:forEach items="${threats}" var="threat">
                    <tr>
                        <td><c:out value="${threat.uuid}"/></td>
                        <td><c:out value="${threat.login}"/></td>
                        <td><c:out value="${threat.type.threatType}"/></td>
                        <td><c:out value="${threat.description}"/></td>
                        <td>
                            <c:forEach items="${threat.votes}" var="vote">
                                <c:out value="${vote.numberOfStars}"/>
                            </c:forEach>
                        </td>
                        <td><c:out value="${threat.coordinates.vertical}"/>;<c:out
                                value="${threat.coordinates.horizontal}"/></td>
                        <td><c:out value="${threat.isApproved}"/></td>
                        <c:choose>
                            <c:when test="${threat.pathToPhoto ne null}">
                                <td>
                                    <button class="btn btn-default" onclick="showImage('${threat.uuid}')">show</button>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>No photo</td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${threat.isApproved eq true}">
                                <td>
                                    <button class="btn btn-default" onclick="disapprove('${threat.uuid}')">Disapprove
                                    </button>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <button class="btn btn-default" onclick="approve('${threat.uuid}')">Approve</button>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <td>
                            <button class="btn btn-default" onclick="deleteThreat('${threat.uuid}')">delete</button>
                        </td>
                        <td>
                            <button class="btn btn-default" onclick="location.href='getThreat/?uuid=${threat.uuid}'">
                                edit
                            </button>
                        </td>
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
