<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

  <script>
    function addVote() {
      $.ajax({
        type: "POST",
        url: "addVoteForThreat",
        dataType: 'text',
        data: {
          stars: $("#stars").val(),
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

  <title>Add vote</title>
</head>
<body>
<div class="panel panel-primary">
  <div class="panel-heading">Add vote</div>
  <button class="btn btn-default" onclick="window.location.href='/TrafficThreat'">Go to main page</button>


  <input type="number" id="stars" class="form-control" placeholder="Number of stars">
  <input type="text" id="uuid" class="form-control" placeholder="Thread uuid">


  <button onclick="addVote()" class="btn btn-default">Add Vote</button>

</div>
<div id="alert_placeholder"></div>
</body>
</html>
