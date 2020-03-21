<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="diseases">

    <h2>Disease Information</h2>


    <table class="table table-striped">
        <tr>
        
        </tr>
        <tr>
            <th>Symptoms</th>
            <td>
                <c:out value="${disease.symptoms}" />
            </td>
        </tr>
        <tr>
            <th>Severity</th>
            <td>
                <c:out value="${disease.severity}" />
            </td>
        </tr>
        <tr>
            <th>Cure</th>
            <td>
                <c:out value="${disease.cure}" />
            </td>
        </tr>
    </table>
    

    <spring:url value="{diseaseId}/edit" var="editUrl">
        <spring:param name="diseaseId" value="${disease.id}" />
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Disease</a>

</petclinic:layout>