<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<html>
<head>
    <title>Background color chooser</title>

    <style>
        body {
            background-color: ${pickedBgColor};
        }
    </style>
</head>
<h2>
    Choose a color:
</h2> <br>
<body>
<a href="${pageContext.request.contextPath}/setcolor?color=white">WHITE</a>
<a href="${pageContext.request.contextPath}/setcolor?color=red">RED</a>
<a href="${pageContext.request.contextPath}/setcolor?color=green">GREEN</a>
<a href="${pageContext.request.contextPath}/setcolor?color=cyan">CYAN</a>
</body>
</html>
