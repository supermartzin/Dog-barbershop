<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="405 - Method not supported">
    <jsp:attribute name="body">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1>Oops :-(</h1>
                    <span>Sorry, an error has occured, invalid HTTP method!</span>
                </div>
            </div>
        </div>
    </jsp:attribute>
</my:pagetemplate>