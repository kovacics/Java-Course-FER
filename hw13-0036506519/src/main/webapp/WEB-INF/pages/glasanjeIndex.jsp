<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Glasanje</title>
    <style>
        body {
            background-color: ${pickedBgColor};
        }
    </style>
</head>

<body>

<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
    glasali!</p>

<ol>
    <c:forEach var="band" items="${bands}">
        <li><a href="glasanje-glasaj?id=${band.id}">${band.name}</a></li>
    </c:forEach>
</ol>

</body>
</html>
