<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>New entry</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css"/>

    <style>
        body {
            background-color: #e6e6e6;
        }

        .addBlogForm {
            background-color: #a9ddff;
        }

        .addBlogForm input[type=submit] {
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
        }

        .addBlogForm input[type=text], .login textarea {
            border-radius: 5px;
            margin: 10px;
        }

        .addBlogForm input[type=text]:focus {
            border: red 2px solid;
        }

    </style>
</head>

<body>

<c:choose>
    <c:when test="${sessionScope['current.user.id'] != null}">
        <div class=currentUser>
            <div class="userName">
                Current user: ${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}
            </div>
        </div>

        <div class="logOut">
            <a href="${pageContext.request.contextPath}/servleti/logout">Log out.</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class=currentUser>
            <div class="userName">
                Not loged in.
            </div>
        </div>
    </c:otherwise>
</c:choose>

<form action="" method="post">

    <div class="addBlogForm">
        <div>
            <div class="formLabel">Title</div>
            <input type="text" name="title" size="30" value="${form.title}">
            <c:if test="${form.errorExist('title')}">
                <div class="greska">
                    <c:out value="${form.getErrorFor('title')}"/>
                </div>
            </c:if>
        </div>
        <div>
            <div class="formLabel">Text</div>
            <textarea name="text" rows="5" cols="30">
                ${form.text}
            </textarea>
            <c:if test="${form.errorExist('text')}">
                <div class="greska">
                    <c:out value="${form.getErrorFor('text')}"/>
                </div>
            </c:if>
        </div>
        <div class="formControls">
            <span class="formLabel"></span>
            <input type="submit" value="Save blog.">
            <input type="hidden" name="newEntry" value="fromJsp">
        </div>
    </div>
</form>

</body>
</html>

