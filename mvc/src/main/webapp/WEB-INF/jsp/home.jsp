<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Dog barbershop">
<jsp:attribute name="body">

    <div class="jumbotron">
        <h1>Dog barbershop!</h1>
        <p class="lead">Welcome to our Dog barbershop website.</p>
        <%--<p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/services"--%>
              <%--role="button">Custmer</a></p>--%>
        <%--<p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/shopping/show"--%>
              <%--role="button">Employee</a></p>--%>
    </div>

</jsp:attribute>
</my:pagetemplate>
