
<%@include file="header.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login Page</title>
    </head>
    <body onload="document.loginForm.username.focus();">
        <div class="Container">
            <center>
                <h2>Login</h2>
            </center>
            <div id="login-box">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                        <span class="sr-only">Error:</span>
                        ${error}
                        </div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="alert alert-info" role="alert">
                        <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
                        <span class="sr-only">Message:</span>
                        ${msg}
                    </div>
                </c:if>
                <form class="form-horizontal" name="loginForm" action="<c:url value="/j_spring_security_check" />" method="POST">
                    <div class="form-group">
                        <label class="control-label col-sm-2">Login:</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="focusedInput" name="username" type="text" placeholder="Enter login">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="pwd">Password:</label>
                        <div class="col-sm-10">          
                            <input type="password" class="form-control" id="pwd" name="password" placeholder="Enter password">
                        </div>
                    </div>
                    <div class="form-group">        
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" name="submit" class="btn btn-default">Submit</button>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            </div>
        </div>
    </body>
</html>