<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Dogs">
<jsp:attribute name="body">
    <table class="table">
        <caption>Dogs</caption>
        <thead>
        <tr>
            <th>Name</th>
            <th>Breed</th>
            <th>Age</th>
            <th>Customer</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${dogs}" var="dog">
            <tr>
                <td>
                    <my:a href="/dogs/detail/${dog.id}">
                        <c:out value="${dog.name}"/>
                    </my:a>
                </td>
                <td><c:out value="${dog.breed}"/></td>
                <td><c:out value="${dog.age}"/></td>
                <td>
                    <my:a href="/customers/detail/${dog.customer.id}">
                        <c:out value="${dog.customer.firstName} ${dog.customer.lastName}"/>
                    </my:a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>