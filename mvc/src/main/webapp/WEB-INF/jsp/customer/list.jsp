<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Customers">
<jsp:attribute name="body">

    <my:a href="/customer/new" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New customer
    </my:a>

    <table class="table">
        <caption>Customers</caption>
        <thead>
        <tr>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th>Phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${customers}" var="user">
            <tr>
                <td>
                    <my:a href="/customer/detail/${user.id}">
                        <c:out value="${user.firstName}"/>
                    </my:a>
                </td>
                <td>
                    <my:a href="/customer/detail/${user.id}">
                        <c:out value="${user.lastName}"/>
                    </my:a>
                </td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.phone}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>