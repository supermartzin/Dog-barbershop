<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Employees">
<jsp:attribute name="body">

    <my:a href="/employee/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New employee
    </my:a>

    <table class="table">
        <caption>Employees</caption>
        <thead>
        <tr>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th>Phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="emp">
            <tr>
                <td>
                    <my:a href="/employee/detail/${emp.id}">
                        <c:out value="${emp.firstName}"/>
                    </my:a>
                </td>
                <td>
                    <my:a href="/employee/detail/${emp.id}">
                        <c:out value="${emp.lastName}"/>
                    </my:a>
                </td>
                <td><c:out value="${emp.email}"/></td>
                <td><c:out value="${emp.phone}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>