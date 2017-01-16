<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Orders">
<jsp:attribute name="body">

    <div class="btn-group" role="group" aria-label="filter">
        <my:a href="/order/list/all" class="btn btn-default ${filter=='all'?'active':''}">All</my:a>
        <my:a href="/order/list/done" class="btn btn-default ${filter=='done'?'active':''}">Done</my:a>
        <my:a href="/order/list/waiting" class="btn btn-default ${filter=='waiting'?'active':''}">Waiting</my:a>
    </div>

    <table class="table">
        <thead>
        <tr>
            <th>Status</th>
            <th>Service</th>
            <th>Dog</th>
            <th>Employee</th>
            <th>Detail</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td><c:out value="${order.status}"/></td>
                    <td><c:out value="${order.service.title}"/></td>
                    <td>
                        <my:a href="/dog/detail/${order.dog.id}">
                            <c:out value="${order.dog.name}"/>
                        </my:a>
                    </td>
                    <td>
                        <my:a href="/employee/detail/${order.employee.id}">
                            <c:out value="${order.employee.firstName} ${order.employee.lastName}"/>
                        </my:a>
                    </td>
                    <td>
                        <my:a href="/order/detail/${order.id}">
                            Detail
                        </my:a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>