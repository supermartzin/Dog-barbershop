<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${customer.firstName} ${customer.lastName}"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <div class="row">
        <div class="col-xs-6">
            <ul>
                <li>Username: <c:out value="${customer.username}"/></li>
                <li>Email: <c:out value="${customer.email}"/></li>
                <li>Phone: <c:out value="${customer.phone}"/></li>
                <li>Address: <my:address address="${customer.address}"/></li>
            </ul>
        </div>
        <div class="col-xs-6">
            <h3>Dogs</h3>
            <my:a href="/dog/new" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                New dog
            </my:a>
            <table class="table">
                <caption>${customer.firstName} ${customer.lastName} 's dogs</caption>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Breed</th>
                        <th>Age</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${customer.dogs}" var="dog">
                    <tr>
                        <td>
                            <my:a href="/dog/detail/${dog.id}">
                                <c:out value="${dog.name}"/>
                            </my:a>
                        </td>
                        <td><c:out value="${dog.breed}"/></td>
                        <td><c:out value="${dog.age}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <table class="table">
                <caption>${customer.firstName} ${customer.lastName} 's orders</caption>
                <thead>
                    <tr>
                        <th>Status</th>
                        <th>Service</th>
                        <th>Dog</th>
                        <th>Employee</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td><c:out value="${order.status}"/></td>
                            <td><c:out value="${order.service.title}"/></td>
                            <td>
                                <my:a href="/order/detail/${order.dog.id}">
                                    <c:out value="${order.dog.name}"/>
                                </my:a>
                            </td>
                            <td>
                                <my:a href="/employee/detail/${order.employee.id}">
                                    <c:out value="${order.employee.firstName} ${order.employee.lastName}"/>
                                </my:a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/customer/delete/${customer.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/customer/update/${customer.id}">
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
</jsp:attribute>
</my:pagetemplate>