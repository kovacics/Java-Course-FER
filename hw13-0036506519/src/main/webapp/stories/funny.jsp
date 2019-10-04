<%@ page import="java.awt.*" %>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<%
    Random rand = new Random();
    int r = rand.nextInt(255);
    int g = rand.nextInt(255);
    int b = rand.nextInt(255);
    Color randomColor = new Color(r, g, b);
    int hex = randomColor.getRGB();
    String color = "#" + Integer.toHexString(hex & 0xffffff);
%>

<head>
    <title>Funny story</title>

    <style>
        body {
            background-color: ${pickedBgColor};
        }

        h2 {
            color: <%=color%>;
        }
    </style>
</head>

<body>
<h2>Završit ću Javu</h2>
</body>
</html>
