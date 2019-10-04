<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Rezultati</title>
    <style type="text/css">
        table.rez td {
            text-align: center;
        }
        body {
            background-color: ${pickedBgColor};
        }
    </style>
</head>

<body>

<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja.</p>

<table border="1" cellspacing="0" class="rez">
    <thead>
    <tr>
        <th>Bend</th>
        <th>Broj glasova</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="record" items="${records}">
        <tr>
            <td>${record.band.name}</td>
            <td>${record.votes}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400"/>

<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

<h2>Razno</h2>
<p>Primjeri pjesama pobjedničkih bendova:</p>
<ul>
    <c:forEach var="record" items="${winners}">
        <li><a href=${record.song} target="_blank">${record.name}</a></li>
    </c:forEach>
</ul>
</body>
</html>