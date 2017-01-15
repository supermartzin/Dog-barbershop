<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Services">
<jsp:attribute name="body">

    <div class="row">
        <c:forEach items="${services}" var="service">
        <div class="col-sm-3 col-md-3">
            <div class="well">
                <h2 class="text-muted"><c:out value="${service.title}"/></h2>
                <p class="price">Length of service: <c:out value="${service.length}"/> minutes</p>
                <hr>
                <h3 class="price">&#8364; <c:out value="${service.price}"/></h3>
            </div>
        </div>
        </c:forEach>
    </div>

</jsp:attribute>
</my:pagetemplate>