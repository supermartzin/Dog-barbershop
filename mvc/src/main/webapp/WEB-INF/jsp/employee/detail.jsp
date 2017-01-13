<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${employee.firstName} ${employee.lastName}"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <div class="row">
        <div class="col-xs-6">
            <ul>
                <li>Username: <c:out value="${employee.username}"/></li>
                <li>Email: <c:out value="${employee.email}"/></li>
                <li>Phone: <c:out value="${employee.phone}"/></li>
                <li>Address: <my:address address="${employee.address}"/></li>
                <li>Salary: <c:out value="${employee.salary}"/></li>
            </ul>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>