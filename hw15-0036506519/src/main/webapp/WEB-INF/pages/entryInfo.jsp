<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Entry info</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css"/>


    <style type="text/css">

        .wholeContainer {
            margin: 30px;
        }

        .blogContainer {
            border: 2px black solid;
            border-radius: 10px;
        }

        .blogEntry {
            padding: 10px 25px;
            border-bottom: 2px black solid;
        }

        .blogEntry h2 {
            margin-top: 0;
        }

        .comments {
            background-color: #e6e6e6;

            padding: 10px 50px;
        }

        .commentEntry {
            background-color: #a9ddff;
            padding: 10px;
            margin: 20px;
            border: 1px #a6a6a6 solid;
            border-radius: 7px;
        }

        .comments ul {
            list-style-type: none;
            padding-left: 5px;
        }

        .currentUser {
            margin: 10px;
            font-weight: bold;
        }

        .addComment {
            background-color: #b3cce6;
            border-radius: 3px;
            margin: 10px 60px;
            padding: 20px;
            border: 1px black solid;
        }

        .addComment input[type=submit] {
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
            background-color: #a9ddff;
        }

        .addComment input[type=text], .login textarea {
            border-radius: 5px;
            margin: 10px;
        }

        .addComment input[type=text]:focus {
            border: red 2px solid;
        }

        .commentMessage {
        }

        .entryTitle {
            text-align: center;
        }

        .entryText {
            font-family: sans-serif;
        }

        .goHomeLabel {
            font-size: 1.2em;
            text-decoration: none;
            color: black;
            padding: 10px 60px;
        }

        hr {
            border-color: #737373;
        }

        .editingEntry {
            text-align: right;
            padding: 25px;
        }

        .editingEntry a:link, .editingEntry a:visited {
            text-decoration: none;
            color: blue;
            font-size: 1.1em;
        }

        .editingEntry a:hover {
            color: orange;
        }

    </style>
</head>
<body>

<div class="wholeContainer">


    <div class="currentUser">

        <c:choose>
            <c:when test="${sessionScope['current.user.id'] != null}">
                <div class=currentUser>
                    <span class="userName">Current user: ${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}</span>
                </div>

                <div class=logOut>
                    <a href="${pageContext.request.contextPath}/servleti/logout">Log out.</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class=currentUser>
                    <span class="userName">Not logged in.</span>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="blogAndComments">

        <div class="blogContainer">

            <div class="blogEntry">
                <div class="entryTitle">
                    <h2>${entry.title}</h2>
                </div>
                <div class="entryText">
                    <p>${entry.text}</p>
                </div>

                <c:if test="${sessionScope['current.user.id'] == entry.creator.id}">
                    <div class="editingEntry">
                        <a href="${pageContext.request.contextPath}/servleti/author/${entry.creator.nick}/edit/${entry.id}">
                            Edit entry
                        </a>
                    </div>

                </c:if>
            </div>
            <div class="comments">
                <div class="commentsLabel"><h3>Comments:</h3></div>
                <ul>
                    <c:forEach items="${entry.comments}" var="comment">
                        <li>
                            <div class="commentEntry">
                                <div class="commentMessage">
                                        ${comment.message}
                                </div>
                                <hr>
                                <div class="commentEmail">
                                    By: ${comment.usersEMail}<br>
                                </div>
                                <div class="commentDate">
                                    Posted on: ${comment.postedOn}<br>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>


        <div class="addComment">
            <form action="" method="post">
                <div>
                    <div>
                        <div class="formLabel">Message</div>
                        <textarea name="message" rows="5" cols="30"></textarea>
                    </div>
                    <c:if test="${form.errorExist('message')}">
                        <div class="greska">
                            <c:out value="${form.getErrorFor('message')}"/>
                        </div>
                    </c:if>
                </div>

                <c:if test="${sessionScope['current.user.id'] == null}">
                    <div>
                        <div>
                            <div class="formLabel">Email</div>
                            <input type="text" name="email" size="20">
                        </div>
                        <c:if test="${form.errorExist('email')}">
                            <div class="greska">
                                <c:out value="${form.getErrorFor('email')}"/>
                            </div>
                        </c:if>
                    </div>
                </c:if>

                <div class="formControls">
                    <span class="formLabel">&nbsp;</span>
                    <input type="submit" value="Add comment">
                    <input type="hidden" name="comment" value="fromJsp">

                </div>
            </form>
        </div>

        <div class="goHomeLabel">
            <a href="${pageContext.request.contextPath}/servleti/main">Go home.</a>
        </div>
    </div>
</div>

</body>
</html>
