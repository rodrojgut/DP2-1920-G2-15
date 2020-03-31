<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="chips">

    <h2>Chip Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Serial number</th>
            <td><b><c:out value="${chip.serialNumber}"/></b></td>
        </tr>
        <tr>
            <th>Model</th>
            <td><c:out value="${chip.model}"/></td>
        </tr>
        <tr>
            <th>Geolocatable</th>
            <td><c:out value="${chip.geolocatable}"/></td>
        </tr>
        <tr>
        	<td>
        		<spring:url value="/owners/{ownerId}/pets/{petId}/chips/{chipId}/delete" var="deleteUrl">
                	<spring:param name="ownerId" value="${chip.pet.owner.id}"/>
                	<spring:param name="petId" value="${chip.pet.id}"/>
                	<spring:param name="chipId" value="${chip.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(deleteUrl)}">Delete chip</a>
            </td>
        </tr>
    </table>
</petclinic:layout>