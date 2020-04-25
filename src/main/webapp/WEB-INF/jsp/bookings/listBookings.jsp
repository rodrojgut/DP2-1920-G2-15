<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="bookings">
    <h2>Bookings</h2>
	
	<c:if test="${message}">
		<c:out value="${message}"></c:out>
	</c:if>
	

    <spring:url value="/bookings/new" var="createUrl">
  	</spring:url>
    <a href="${fn:escapeXml(createUrl)}" class="btn btn-default">Book a room</a>
	<br></br>
    <table id="bookingsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Date</th>
            <th style="width: 100px;">Vet</th>
            <th style="width: 100px;">Owner</th>
            <th style="width: 100px">Pet</th>
            <th style="width: 50px">Room</th>
            <sec:authorize access= "hasAuthority('veterinarian')">
            <th style="width: 100px">Delete</th>
            <th style="width: 100px">Edit</th>
            </sec:authorize>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookings}" var="booking">
            <tr>
            	<td>
                    <c:out value="${booking.date}"/>
                </td>
                <td>
                    <c:out value="${booking.vet.firstName} ${booking.vet.lastName}"/>
                </td>
                <td>
                    <c:out value="${booking.owner.firstName} ${booking.owner.lastName}"/>
                </td>
                <td>
                    <c:out value="${booking.pet.name}"/>
                </td>
                <td>
                    <c:out value="${booking.room.id}"/>
                </td>
                <sec:authorize access= "hasAuthority('veterinarian')">
                <td>
               		<spring:url value="/bookings/{bookingId}/delete" var="deleteUrl">
        				<spring:param name="bookingId" value="${booking.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(deleteUrl)}">Delete</a>
                </td>
                </sec:authorize>
                <sec:authorize access= "hasAuthority('veterinarian')">
                <td>
               		<spring:url value="/bookings/edit/{bookingId}" var="editUrl">
        				<spring:param name="bookingId" value="${booking.id}"/>
    				</spring:url>
    				<a href="${fn:escapeXml(editUrl)}">Edit</a>
                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
