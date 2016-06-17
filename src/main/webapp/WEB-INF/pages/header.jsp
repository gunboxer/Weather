
<%@ page contentType="text/html" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet">
        <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.9.1.js" />"> </script>
        <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />"> </script>
    </head>
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/medialist">Weather app</a>
                </div>
                <div>
                    
                    <ul class="nav navbar-nav navbar-right">
                        <c:choose>
                            <c:when test="${isAdministrator || isUser}">
                                <li>
                                    <p class="navbar-text">
                                        <b>Welcome, <c:out value="${login}"/> 
                                            <c:choose>
                                                <c:when test="${isAdministrator}">
                                                    <span class="glyphicon glyphicon-king"></span>
                                                </c:when>
                                                <c:when test="${isUser}">
                                                    <span class="glyphicon glyphicon-pawn"></span>
                                                </c:when>
                                            </c:choose>
                                        </b>
                                    </p>
                                <li>
                                <li><a href="/j_spring_security_logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
    </body>
</html>