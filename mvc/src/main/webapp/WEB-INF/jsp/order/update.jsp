<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Update order">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/order/update/${orderUpdate.id}"
               modelAttribute="orderUpdate" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="time" cssClass="col-sm-2 control-label">Time</form:label>
            <div class="col-sm-10">
                <form:input path="time" cssClass="form-control"/>
                <form:errors path="time" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="order.dog" cssClass="col-sm-2 control-label">Dog</form:label>
            <div class="col-sm-10">
                <form:select path="order.dog" cssClass="form-control">
                    <c:forEach items="${dogs}" var="dog">
                        <form:option value="${dog.id}">${dog.name}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="order.dog" cssClass="error"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="order.service" cssClass="col-sm-2 control-label">Service</form:label>
            <div class="col-sm-10">
                <form:select path="order.service" cssClass="form-control">
                    <c:forEach items="${services}" var="service">
                        <form:option value="${service.id}">${service.title}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="order.service" cssClass="error"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="order.employee" cssClass="col-sm-2 control-label">Dog</form:label>
            <div class="col-sm-10">
                <form:select path="order.employee" cssClass="form-control">
                    <c:forEach items="${employees}" var="employee">
                        <form:option value="${employee.id}">${employee.firstName} ${employee.lastName}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="order.employee" cssClass="error"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Update Order</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>