<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <style type="text/css">
        table.rez td {
            text-align: center;
        }

        h1 {
            text-align: center;
            background-color: deepskyblue;
        }

        body {
            background-color: cadetblue;
        }
    </style>
</head>

<body>

<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja.</p>
<table border="1" cellspacing="0" class="rez">
    <thead>
    <tr>
        <th>Opcija</th>
        <th>Broj glasova</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="record" items="${records}">
        <tr>
            <td>${record.optionTitle}</td>
            <td>${record.votesCount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart"
     src="${pageContext.request.contextPath}/servleti/glasanje-grafika?pollID=<%=request.getParameter("pollID")%>"
     width="400" height="400"/>


<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su
    <a href="${pageContext.request.contextPath}/servleti/glasanje-xls?pollID=<%=request.getParameter("pollID")%>">ovdje</a>
</p>
<h2>Razno</h2>

<p>Pobjednici:</p>
<ul>
    <c:forEach var="record" items="${winners}">
        <li><a href="${record.optionLink}" target="_blank">${record.optionTitle}</a></li>
    </c:forEach>
</ul>
</body>
</html>