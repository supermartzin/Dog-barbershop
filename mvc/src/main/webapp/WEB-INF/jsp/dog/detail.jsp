<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${dog.name}"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <form method="post" action="${pageContext.request.contextPath}/dogs/delete/${dog.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>

    <div class="row">
        <div class="col-xs-6">
            <ul>
                <li>Breed: <c:out value="${dog.breed}"/></li>
                <li>Age: <c:out value="${dog.age}"/></li>
                <my:a href="/customers/detail/${dog.customer.id}">
                    <li><c:out value="${dog.customer.firstName} ${dog.customer.lastName}"/></li>
                </my:a>
            </ul>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>