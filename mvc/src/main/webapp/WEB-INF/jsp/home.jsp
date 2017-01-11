<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Dog barbershop">
<jsp:attribute name="body">

    <div class="jumbotron">
        <p class="lead">Welcome to our Dog barbershop.</p>
    </div>

    <div class="row">
        <div class="col-sm-6 business-hours">
            <h2 class="title">Opening Hours</h2>
            <ul class="list-unstyled opening-hours">
                <li>Sunday <span class="pull-right">Closed</span></li>
                <li>Monday <span class="pull-right">9:00-22:00</span></li>
                <li>Tuesday <span class="pull-right">9:00-22:00</span></li>
                <li>Wednesday <span class="pull-right">9:00-22:00</span></li>
                <li>Thursday <span class="pull-right">9:00-22:00</span></li>
                <li>Friday <span class="pull-right">9:00-23:30</span></li>
                <li>Saturday <span class="pull-right">14:00-23:30</span></li>
            </ul>
        </div>
        <div class="col-sm-6 business-hours">
            <img class="img-responsive" src="${pageContext.request.contextPath}/resources/images/dogs.jpg" />
        </div>
    </div>

</jsp:attribute>
</my:pagetemplate>
