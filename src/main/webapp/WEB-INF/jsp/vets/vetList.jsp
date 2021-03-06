<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>




<petclinic:layout pageName="vets">
    <h2>Veterinarians</h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Specialties</th>
             <sec:authorize access= "!hasAuthority('veterinarian')">
            <th>Opinion</th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                <sec:authorize access= "!hasAuthority('veterinarian')">
                <td>
                   	<spring:url value="/opinions/new/{vetId}" var="addOpinion">
                   	<spring:param name="vetId" value="${vet.id}"/>
                   	</spring:url>
                    <a href="${fn:escapeXml(addOpinion)}" class="btn btn-default">Add Opinion</a>
                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
	
	<sec:authorize access= "hasAuthority('owner')||hasAuthority('admin')">
		<spring:url value="/opinions/list" var="listOpinion"></spring:url>
    	<a href="${fn:escapeXml(listOpinion)}" class="btn btn-default">List all opinions</a>
    </sec:authorize>    
    
    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">View as XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
