<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>App info</title>

    <style>
        body {
            background-color: ${pickedBgColor};
        }
    </style>
</head>

<%
    Long startTime = (Long) application.getAttribute("startTime");

    final long passed = System.currentTimeMillis() - startTime;
    final long day = TimeUnit.MILLISECONDS.toDays(passed);
    final long hours = TimeUnit.MILLISECONDS.toHours(passed)
            - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(passed));
    final long minutes = TimeUnit.MILLISECONDS.toMinutes(passed)
            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(passed));
    final long seconds = TimeUnit.MILLISECONDS.toSeconds(passed)
            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(passed));
    final long ms = TimeUnit.MILLISECONDS.toMillis(passed)
            - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(passed));

    String result = String.format("%d Days %d Hours %d Minutes %d Seconds %d Milliseconds",
            day, hours, minutes, seconds, ms);
%>



<body>
This web application is running for <%=result%>.
</body>
</html>
