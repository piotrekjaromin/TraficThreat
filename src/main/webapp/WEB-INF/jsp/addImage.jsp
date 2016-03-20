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
    <title></title>
</head>
<body>

<form method="POST" enctype="multipart/form-data" action="addImage">
    <input type="file" name="file" />
    <input type="text" name="uuid" />
  <input type="submit" value="Upload" />
</form>

</body>
</html>
