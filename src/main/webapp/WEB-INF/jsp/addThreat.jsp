<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href="<c:url value='main.css'/>" rel="stylesheet" type="text/css">
    <title>Add threat</title>

    <script>

        function checkData() {
            if($("#typeOfThreat").val() == null) {
                $('#alert_placeholder').html('<div class="alert alert-danger">Error: no choosen type of threat </div>')
                return;
            }
            if($("#description").val() == "") {
                $('#alert_placeholder').html('<div class="alert alert-danger">Error: no description</div>')
                return;
            }
            if($("#coordinates").val() == "" || $("#coordinates").val().split(';').length!=2) {
                $('#alert_placeholder').html('<div class="alert alert-danger">Error: bad coordinates</div>')
                return;
            }

            if($("#location").val() == "" || $("#location").val().split(';').length!=2) {
                $('#alert_placeholder').html('<div class="alert alert-danger">Error: bad location</div>')
                return;
            }

            addThreat();

        }

        function addThreat() {
            $.ajax({
                type: "POST",
                url: "user/addThreat",
                dataType: 'text',
                data: {
                    typeOfThreat: $("#typeOfThreat").val(),
                    description: $("#description").val(),
                    coordinates: $("#coordinates").val(),
                    location: $("#location").val()

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


    </script>

</head>
<body>

<div class="panel panel-primary">
    <div class="panel-heading">
        Add threat
        <button class="btn btn-default goToMainPage" onclick="window.location.href='/TrafficThreat'">Go to main page
        </button>
    </div>

    <div class="panel-body">

            <select class="form-control" id="typeOfThreat" required>
                <option value="" disabled selected hidden>Type of threat</option>
                <option value="Stale">Stale</option>
                <option value="Krotkotrwale">Krotkotrwale</option>
            </select>
            <input type="text" id="description" class="form-control" placeholder="Description">
            <input type="text" id="coordinates" class="form-control" placeholder="Coordinates">
            <input type="text" id="location" class="form-control" placeholder="Location">


            <button onclick="checkData()" class="btn btn-default">Add threat</button>
            <div id="alert_placeholder"></div>

    </div>
</div>
</body>
</html>
