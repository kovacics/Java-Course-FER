<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Home</title>
    <style>
        body {
            background-color: ${pickedBgColor};
        }
    </style>
</head>

<body>

<a href="colors.jsp">Background color chooser</a><br>
<a href="${pageContext.request.contextPath}/trigonometric?a=0&b=90">Calculate angles for first quadrant.</a>

<form action="trigonometric" method="GET">
    Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
    Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
    <input type="submit" value="Tabeliraj">
    <input type="reset" value="Reset">
</form>

<a href="stories/funny.jsp">Funny story</a><br>
<a href="report.jsp">OS usage</a><br>
<a href="powers?a=1&b=100&n=3">Get XLS</a><br>
<a href="appinfo.jsp">Get running time</a><br>

<h3>
    <a href="glasanje">Glasanje</a><br>
</h3>

</body>
</html>
