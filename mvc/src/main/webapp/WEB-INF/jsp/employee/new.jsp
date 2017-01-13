<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New employee">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/employee/create"
               modelAttribute="employeeCreate" cssClass="form-horizontal">
        <div class="form-group ${firstName_error?'has-error':''}">
            <form:label path="firstName" cssClass="col-sm-2 control-label">First name</form:label>
            <div class="col-sm-10">
                <form:input path="firstName" cssClass="form-control"/>
                <form:errors path="firstName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${lastName_error?'has-error':''}">
            <form:label path="lastName" cssClass="col-sm-2 control-label">Last name</form:label>
            <div class="col-sm-10">
                <form:input path="lastName" cssClass="form-control"/>
                <form:errors path="lastName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="username" cssClass="col-sm-2 control-label">Username</form:label>
            <div class="col-sm-10">
                <form:input path="username" cssClass="form-control"/>
                <form:errors path="username" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="password" cssClass="col-sm-2 control-label">Password</form:label>
            <div class="col-sm-10">
                <form:input path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="email" cssClass="col-sm-2 control-label">Email Address</form:label>
            <div class="col-sm-10">
                <form:input path="email" cssClass="form-control"/>
                <form:errors path="email" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="phone" cssClass="col-sm-2 control-label">Phone number</form:label>
            <div class="col-sm-10">
                <form:input path="phone" cssClass="form-control"/>
                <form:errors path="phone" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="salary" cssClass="col-sm-2 control-label">Salary</form:label>
            <div class="col-sm-10">
                <form:input path="salary" cssClass="form-control"/>
                <form:errors path="salary" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="address.street" cssClass="col-sm-2 control-label">Street</form:label>
            <div class="col-sm-10">
                <form:input path="address.street" cssClass="form-control"/>
                <form:errors path="address.street" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="address.number" cssClass="col-sm-2 control-label">Street number</form:label>
            <div class="col-sm-10">
                <form:input path="address.number" cssClass="form-control"/>
                <form:errors path="address.number" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="address.city" cssClass="col-sm-2 control-label">City</form:label>
            <div class="col-sm-10">
                <form:input path="address.city" cssClass="form-control"/>
                <form:errors path="address.city" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="address.country" cssClass="col-sm-2 control-label">Country</form:label>
            <div class="col-sm-10">
                <form:input path="address.country" cssClass="form-control"/>
                <form:errors path="address.country" cssClass="help-block"/>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Create employee</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>