<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <style>
        h1 {
            background-color: deepskyblue;
            text-align: center;
        }

        body {
            background-color: cadetblue;
        }
    </style>
</head>
<body>

<h1>${pollInfo.title}</h1>

<p>${pollInfo.message}</p>

<ol>
    <c:forEach var="option" items="${pollOptions}">
        <li>
            <a href="${pageContext.request.contextPath}/servleti/glasanje-glasaj?id=${option.id}">${option.optionTitle}</a>
        </li>
    </c:forEach>
</ol>
</body>
</html>
