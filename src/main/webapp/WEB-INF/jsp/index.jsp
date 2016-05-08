<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="googlemaps" uri="/WEB-INF/googlemaps.tld" %>

<html>
<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBxPnLikp9JZlQjap5fpX4L6y3eeCNPz9o&callback=initMap">
    </script>
    <title>Traffic Threat</title>

    <script>


        function showImage(threatUuid) {
            var result;
            $.ajax({
                type: "POST",
                url: "/TrafficThreat/showImage",
                dataType: 'text',
                data: {
                    uuid: threatUuid
                },
                async: false,
                success: function (response) {
                   result = response;
                }
            });
            return result;
        };

        function initMap() {
            var myLatLng = {lat: 50.0618971, lng: 19.93675600000006};
            var counter = 0;
            var marker;
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 14,
                center: myLatLng
            });

            var infowindow = new google.maps.InfoWindow();

            <c:forEach items="${approvedThreats}" var="threat">
            counter = counter + 1;

            marker = new google.maps.Marker({
                position: new google.maps.LatLng(${threat.coordinates.horizontal}, ${threat.coordinates.vertical}),
                map: map,
                title: '${threat.description}'
            });

            google.maps.event.addListener(marker, 'click', (function (marker, counter) {
                return function () {
                    var context = "type: ${threat.type.threatType}<br>";
                    context += "description: ${threat.description}<br>";
                    context += "date: ${threat.date}<br>";
                    context += "<img height='100' alt='No image' src='data:image/png;base64," + showImage('${threat.uuid}') + "'/><br>";
                    context += "<button onclick='location.href=\"/TrafficThreat/getThreatDetails/?uuid=${threat.uuid}\"'>show details</button>";

                    infowindow.setContent(context);
                    infowindow.open(map, marker);
                }
            })(marker, counter));
            </c:forEach>
        }
    </script>

</head>

<body onload="initMap()">

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
            <button class='btn btnMenu btn-primary' onclick="location.href='user/addImage'">Add image</button>
            <button class='btn btnMenu btn-primary' onclick="location.href='showLogs'">Show logs</button>
        </sec:authorize>

        <sec:authorize access="hasRole('ADMIN')">
            <button class='btn btnMenu btn-primary' onclick="location.href='admin/showUsers'">Show users</button>
        </sec:authorize>

        <br/>
        <br/>

        <div id="map" style="width: 90%; height: 80%"></div>
    </div>
</div>
</body>
</html>