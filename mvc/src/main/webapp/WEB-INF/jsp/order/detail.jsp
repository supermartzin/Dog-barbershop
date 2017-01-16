<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Order ${order.id} detail">
<jsp:attribute name="body">

        <table class="table">
            <caption>Order</caption>
            <thead>
            <tr>
                <th>Status</th>
                <th>Service</th>
                <th>Dog</th>
                <th>Employee</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><c:out value="${order.status}"/></td>
                <td><c:out value="${order.service.title}"/></td>
                <td>
                    <my:a href="/dog/detail/${dog.id}">
                        <c:out value="${order.dog.name}"/>
                    </my:a>
                </td>
                <td>
                    <my:a href="/employee/detail/${employee.id}">
                        <c:out value="${employee.firstName} ${employee.lastName}"/>
                    </my:a>
                </td>
            </tr>
            </tbody>
        </table>

    <div class="row">
    <c:choose>
        <c:when test="${order.status==false}">
            <div class="col-xs-1">
            <form method="post" action="${pageContext.request.contextPath}/order/markDone/${order.id}">
                <button type="submit" class="btn btn-primary">Mark done</button>
            </form>
            </div>
        </c:when>
    </c:choose>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/order/delete/${order.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>
    <form method="get" action="${pageContext.request.contextPath}/order/update/${order.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>

</jsp:attribute>
</my:pagetemplate>