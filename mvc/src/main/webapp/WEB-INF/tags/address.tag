<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" dynamic-attributes="attr" %>
<%@ attribute name="address" required="true" type="cz.muni.fi.pa165.dto.AddressDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<address>
    <c:out value='${address.street} '/><c:out value='${address.number}'/><br />
    <c:out value='${address.city} '/> <c:out value='${address.postalCode}'/> <br />
    <c:out value='${address.country}'/> <br />
</address>