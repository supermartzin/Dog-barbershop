<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${dog.name}"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <div class="row">
        <div class="col-xs-6">
            <ul>
                <li>Breed: <c:out value="${dog.breed}"/></li>
                <li>Age: <c:out value="${dog.age}"/></li>
                <my:a href="/customer/detail/${dog.customer.id}">
                    <li><c:out value="${dog.customer.firstName} ${dog.customer.lastName}"/></li>
                </my:a>
            </ul>
        </div>
    </div>

    <div>
        <table class="table">
            <caption>Orders for ${dog.name}</caption>
            <thead>
            <tr>
                <th>Status</th>
                <th>Service</th>
                <th>Employee</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td><c:out value="${order.status}"/></td>
                        <td><c:out value="${order.service.title}"/></td>
                        <td>
                            <my:a href="/employee/detail/${employee.id}">
                                <c:out value="${order.employee.firstName} ${order.employee.lastName}"/>
                            </my:a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/dog/delete/${dog.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>
    <form method="get" action="${pageContext.request.contextPath}/dog/update/${dog.id}">
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
</jsp:attribute>
</my:pagetemplate>