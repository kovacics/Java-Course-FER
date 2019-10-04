<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Error</title>
    <style>
        h2 {
            background-color: red;
        }

        a {
            font-size: 150%;
        }
    </style>
</head>
<body>
<h2>
    Error happened.
</h2>
<p>
    ${error}
</p>
<a href="${pageContext.request.contextPath}/servleti/glasanje">Home page.</a>
</body>
</html>
