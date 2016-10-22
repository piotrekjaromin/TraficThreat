<%--
  Created by IntelliJ IDEA.
  User: piotrek
  Date: 19.03.16
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add mage</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="panel panel-primary">
    <div class="panel-heading">
        Add Image
        <button class="btn btn-default goToMainPage" onclick="window.location.href='/TrafficThreat'">Go to main page</button>
    </div>
    <div class="panel-body">
        <form method="POST" enctype="multipart/form-data" action="addImage">
            <input type="file" name="file"/>
            <input class="form-control" type="text" name="uuid" placeholder="uuid"/>
            <input class="btn btn-default" type="submit" value="Upload"/>
        </form>
    </div>
</div>

</body>
</html>
