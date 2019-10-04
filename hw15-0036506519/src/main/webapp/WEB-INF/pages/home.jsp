<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Home</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css"/>
    <style>

        body {
            background-color: #e6e6e6;
        }

        h1 {
            text-align: center;
        }

        .wholeContainer {
            background-color: #99ccff;
            margin: 30px;
        }

        .registeredUsers {
            padding: 20px;
            margin: 20px;
            text-align: center;
            display: inline-block;
            width: 60%;
            background-color: #a9ddff;
            height: auto;
        }

        .registeredUsers ul {
            list-style-type: none;
        }

        .registeredUsers a:hover {
            color: orange;
        }

        .currentUser {
            padding: 30px;
            margin: 20px;
            display: inline-block;
            background-color: #e6e6e6;
        }

        .login input[type=submit] {
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
            background-color: #99ccff;
        }

        .login input[type=text], .login textarea {
            padding: 5px;
            border-radius: 5px;
        }

        .login input[type=text]:focus {
            border: red 1px solid;
        }

        a {
            text-decoration: none;
        }

    </style>

</head>


<body>
<h1>Welcome to BlogWebApp!</h1>
<div class="wholeContainer">

    <div class="registeredUsers">
        <c:choose>
            <c:when test="${users.isEmpty()}">
                <p>There are no registered users.</p>
            </c:when>
            <c:otherwise>
                <h3>Registered blog users:</h3>
                <c:forEach items="${users}" var="user">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/servleti/author/${user.nick}"> ${user.nick}</a>
                        </li>
                    </ul>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>


    <div class="currentUser">
        <c:choose>
            <c:when test="${sessionScope['current.user.id'] != null}">
                <div class="userName">
                    Current user: ${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}
                </div>

                <div class="logOut">
                    <a href="logout">Log out.</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="userName">
                    Not logged in.
                </div>

                <div class="login">
                    <form action="" method="post">
                        <div>
                            <div>
                                <div class="formLabel">
                                    Nickname
                                </div>
                                <input type="text" name="nick" size="15">
                            </div>
                            <c:if test="${form.errorExist('nick')}">
                                <div class="greska">
                                    <c:out value="${form.getErrorFor('nick')}"/>
                                </div>
                            </c:if>
                        </div>

                        <div>
                            <div>
                                <div class="formLabel">
                                    Password
                                </div>
                                <input type="password" name="password" size="20">
                            </div>
                            <c:if test="${form.errorExist('password')}">
                                <div class="greska">
                                    <c:out value="${form.getErrorFor('password')}"/>
                                </div>
                            </c:if>
                        </div>

                        <div class="formControls">
                            <span class="formLabel">&nbsp;</span>
                            <input type="submit" value="Log in">
                            <input type="hidden" name="login" value="fromJsp">
                        </div>
                    </form>
                </div>

                <div class="register">
                    <div class="registrationMessage">
                        Not registered yet?
                    </div>
                    <a href="register">Register</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</div>


</body>
</html>

