<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="500 - Server error">
    <jsp:attribute name="body">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1>Oops :-(</h1>
                    <span>Sorry, an error has occured, please contact system administrator!</span>
                </div>
            </div>
        </div>
    </jsp:attribute>
</my:pagetemplate>