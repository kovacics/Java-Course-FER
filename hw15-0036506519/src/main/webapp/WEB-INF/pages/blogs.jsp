<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Blog entries</title>

    <style>

        body {
            background-color: #e6e6e6;
        }

        .wholeContainer {
            margin: 25px;
        }

        .currentUser {
            margin: 10px 0;
            font-weight: bold;
        }

        .allBlogs {
            margin: 20px 0;
            padding: 10px 20px;
            background-color: #99ccff;
            border: 1px black solid;
            border-radius: 10px;
        }

        .addingNew {
            margin: 30px;
        }

        a {
            text-decoration: none;
        }

        a:link, a:visited {
            color: black;
        }

        .allBlogs li {
            font-size: 1.1em;
        }

        a:hover {
            color: orange;
        }

        .nick {
            color: #f42525;
            font-size: 1.1em;
        }

        .goHomeLabel {
            font-size: 1.2em;
            text-decoration: none;
            color: black;
        }
    </style>
</head>
<body>
<div class="wholeContainer">

    <div class="currentUser">
        <c:choose>
            <c:when test="${sessionScope['current.user.id'] != null}">
                <div class="userName">
                    Current user: ${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}
                </div>

                <div class="logOut">
                    <a href="${pageContext.request.contextPath}/servleti/logout">Log out.</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="userName">
                    Not logged in.
                </div>
            </c:otherwise>
        </c:choose>
    </div>


    <div class="allBlogs">
        <h2>List of all blogs of the user with nick: <span class="nick">${author}</span></h2> <br>

        <c:choose>
            <c:when test="${blogs.isEmpty()}">
                <p>There are no blogs for this author.</p>
            </c:when>
            <c:otherwise>
                <ol>
                    <c:forEach var="blog" items="${blogs}">
                        <li>
                            <a href="${pageContext.request.contextPath}/servleti/author/${author}/${blog.id}">${blog.title}</a>
                            <br>
                        </li>
                    </c:forEach>
                </ol>
            </c:otherwise>
        </c:choose>
    </div>


    <div class="addingNew">
        <c:if test="${sessionScope['current.user.nick'] eq author}">
            <a href="${pageContext.request.contextPath}/servleti/author/${author}/new">Add new blog entry</a>
        </c:if>
    </div>
    <div class="goHomeLabel">
        <a href="${pageContext.request.contextPath}/servleti/main">Go home.</a>
    </div>
</div>


</body>
</html>
