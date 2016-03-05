
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show threats</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        function insert(){
            $("#users").val(JSON.stringify(${users}, null, 4));
        }

    </script>
</head>
<body onload="insert()">
<textarea id="users" style="width: 100%; height: 100%">

</textarea>
</body>
</html>
