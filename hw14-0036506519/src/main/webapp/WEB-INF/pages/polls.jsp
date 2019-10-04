<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <style>
        h1 {
            text-align: center;
            background-color: deepskyblue;
        }

        body {
            background-color: cadetblue;
        }
    </style>
    <title>Voting app</title>
</head>
<body>
<h1>Welcome to the Voting app!</h1>
<h3>Choose poll in which you want to participate:</h3>

<ol style="font-size: 25px">
    <c:forEach var="poll" items="${pollsList}">
        <li>
            <a style="color: darkslategray; font-size: 25px"
               href="${pageContext.request.contextPath}/servleti/glasanje?pollID=${poll.id}">${poll.title}</a>
        </li>
    </c:forEach>
</ol>


</body>
</html>
