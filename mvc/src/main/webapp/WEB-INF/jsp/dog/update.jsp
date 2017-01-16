<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Update dog">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/dog/update/${dogUpdate.id}"
               modelAttribute="dogUpdate" cssClass="form-horizontal">
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${breed_error?'has-error':''}">
            <form:label path="breed" cssClass="col-sm-2 control-label">Breed</form:label>
            <div class="col-sm-10">
                <form:input path="breed" cssClass="form-control"/>
                <form:errors path="breed" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="age" cssClass="col-sm-2 control-label">Age</form:label>
            <div class="col-sm-10">
                <form:input path="age" cssClass="form-control"/>
                <form:errors path="age" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="customer" cssClass="col-sm-2 control-label">Customer</form:label>
            <div class="col-sm-10">
                <form:select path="customer" cssClass="form-control">
                    <c:forEach items="${customers}" var="c">
                        <form:option value="${c.id}">${c.firstName}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="customer" cssClass="error"/>
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Update dog</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>