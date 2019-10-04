<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forms.css"/>

    <style type="text/css">
        input[type=submit] {
            border-radius: 5px;
            padding: 10px;
            margin: 10px;
        }

        input[type=text] {
            border-radius: 5px;
            margin: 10px;
            padding: 5px;
        }

        input[type=text]:focus {
            border: red 1px solid;
        }

        body {
            background-color: #e6e6e6;
        }
    </style>
</head>


<body>
<form action="register" method="post">

    <div>
        <div>
            <span class="formLabel">First name</span><input type="text" name="firstName" size="5">
        </div>
        <c:if test="${form.errorExist('firstName')}">
            <div class="greska"><c:out value="${form.getErrorFor('firstName')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Last name</span><input type="text" name="lastName" size="5">
        </div>
        <c:if test="${form.errorExist('lastName')}">
            <div class="greska"><c:out value="${form.getErrorFor('lastName')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Email</span><input type="text" name="email" size="5">
        </div>
        <c:if test="${form.errorExist('email')}">
            <div class="greska"><c:out value="${form.getErrorFor('email')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Nick</span><input type="text" name="nick" size="5">
        </div>
        <c:if test="${form.errorExist('nick')}">
            <div class="greska"><c:out value="${form.getErrorFor('nick')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
            <span class="formLabel">Password</span><input type="password" name="password" size="20">
        </div>
        <c:if test="${form.errorExist('password')}">
            <div class="greska"><c:out value="${form.getErrorFor('password')}"/></div>
        </c:if>
    </div>

    <input type="hidden" name="register" value="fromJsp">

    <div class="formControls">
        <span class="formLabel">&nbsp;</span>
        <input type="submit" value="Register">
    </div>
</form>


</body>
</html>

