<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="opinions">
    <h2>Opinions</h2>
	
	<c:if test="${message}">
		<c:out value="${message}"></c:out>
	</c:if>
	
    <table id="opinionsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Vet</th>
            <th style="width: 50px;">Puntuation</th>
            <th style="width: 200px;">Date</th>
            <th style="width: 500px">Commentary</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${opinions}" var="opinion">
            <tr>
                <td>
                    <c:out value="${opinion.vet.firstName} ${opinion.vet.lastName}"/>
                </td>
                <td>
                    <c:out value="${opinion.puntuation}"/>
                </td>
                <td>
                    <c:out value="${opinion.date}"/>
                </td>
                <td>
                    <c:out value="${opinion.comentary}"/>
                </td>
                <c:if test="${!mine}">
                <td>
               		<spring:url value="/opinions/{opinionId}/delete" var="deleteUrl">
        				<spring:param name="opinionId" value="${opinion.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${mine}">
    	<spring:url value="/opinions/listMine" var="listOpinionMine"></spring:url>
    	<a href="${fn:escapeXml(listOpinionMine)}" class="btn btn-default">List my opinions</a>
    </c:if>
</petclinic:layout>
