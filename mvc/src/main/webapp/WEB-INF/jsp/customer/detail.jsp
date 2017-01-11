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
                <li>Email: <c:out value="${customer.username}"/></li>
                <li>Phone: <c:out value="${customer.phone}"/></li>
                <li>Address: <my:address address="${customer.address}"/></li>
            </ul>
        </div>
        <div class="col-xs-6">
            <h3>Dogs</h3>
            <table class="table">
                <caption>Dogs</caption>
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
                        <td><c:out value="${dog.name}"/></td>
                        <td><c:out value="${dog.breed}"/></td>
                        <td><c:out value="${dog.age}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>