<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Login">
    <jsp:attribute name="body">
        <div class="container">

            <c:if test="${error}">
                    <div class="alert alert-danger">
                        <strong>Invalid username or password!</strong>
                    </div>
                </c:if>
            <c:if test="${logout}">
                    <div class="alert alert-info">
                        <strong>Successfully logged out.</strong>
                    </div>
                </c:if>

            <form class="form-signin" action="${pageContext.request.contextPath}/login" method="post">
                <h2 class="form-signin-heading">Please sign in</h2>

                <label for="inputEmail" class="sr-only">Email address</label>
                <input type="text" id="inputEmail" name="username" class="form-control" placeholder="Username" required autofocus>

                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
    </jsp:attribute>
</my:pagetemplate>