<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <title>Trigonometric functions</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        th, td {
            padding: 15px;
            text-align: left;
        }
        body {
            background-color: ${pickedBgColor};
        }
    </style>
</head>

<body>
<table>
    <caption>Trigonometric table</caption>
    <tr>
        <th>Angle</th>
        <th>Sin</th>
        <th>Cos</th>
    </tr>
    <c:forEach var="entry" items="${trigRecords}">
        <tr>
            <td>${entry.angle}</td>
            <td>${entry.sin}</td>
            <td>${entry.cos}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>