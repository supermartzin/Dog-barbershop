<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" dynamic-attributes="attr" %>
<%@ attribute name="address" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<address>
    <c:out value='${address.number}'/> <c:out value='${address.street}'/> <br />
    <c:out value='${address.city}'/> <c:out value='${address.postalCode}'/> <br />
    <c:out value='${address.country}'/> <br />
</address>