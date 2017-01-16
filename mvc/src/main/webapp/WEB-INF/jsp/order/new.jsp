<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New order">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/order/create"
               modelAttribute="orderCreate" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="time" cssClass="col-sm-2 control-label">Time</form:label>
            <div class="col-sm-10">
                <form:input path="time" cssClass="form-control"/>
                <form:errors path="time" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="dog" cssClass="col-sm-2 control-label">Dog</form:label>
            <div class="col-sm-10">
                <form:select path="dog" cssClass="form-control">
                    <c:forEach items="${dogs}" var="dg">
                        <form:option value="${dg.id}">${dg.name}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="dog" cssClass="error"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="service" cssClass="col-sm-2 control-label">Service</form:label>
            <div class="col-sm-10">
                <form:select path="service" cssClass="form-control">
                    <c:forEach items="${services}" var="serv">
                        <form:option value="${serv.id}">${serv.title}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="service" cssClass="error"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="employee" cssClass="col-sm-2 control-label">Employee</form:label>
            <div class="col-sm-10">
                <form:select path="employee" cssClass="form-control">
                    <c:forEach items="${employees}" var="empl">
                        <form:option value="${empl.id}">${empl.firstName} ${empl.lastName}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="employee" cssClass="error"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create Order</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>