<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script>
        function addComment() {
            $.ajax({
                type: "POST",
                url: "addComment",
                dataType: 'text',
                data: {
                    comment: $("#comment").val(),
                    uuid: $("#uuid").val()
                },
                success: function (response) {
                    $(".form-inline").hide();
                    $('#alert_placeholder').html('<div class="alert alert-success">' + response + '</div>')
                },
                error: function (response) {
                    $('#alert_placeholder').html('<div class="alert alert-danger">' + response + '</div>')
                }
            });
        }
        ;
    </script>

    <title>Add comment</title>
</head>
<body>
<div class="panel panel-primary">
    <div class="panel-heading">Add threat</div>
    <button class="btn btn-default" onclick="window.location.href='/TrafficThreat'">Go to main page</button>


    <input type="text" id="comment" class="form-control" placeholder="Comment">
    <input type="text" id="uuid" class="form-control" placeholder="Thread uuid">


    <button onclick="addComment()" class="btn btn-default">Add comment</button>

</div>
<div id="alert_placeholder"></div>
</body>
</html>
