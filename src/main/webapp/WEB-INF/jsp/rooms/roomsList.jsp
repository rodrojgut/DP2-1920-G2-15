<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="rooms">
    <h2>Rooms</h2>

    <table id="roomsTable" class="table table-striped">
        <thead>
            <tr>
                <th style="width: 150px;">Room</th>
                <th style="width: 150px;">Floor</th>
                <th style="width: 200px;">Medical Team</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${rooms}" var="room">
                <tr>

                    <td>
                        <c:out value="${room.id}" />
                    </td>
                    <td>
                        <c:out value="${room.floor}" />
                    </td>
                    <td>
                        <c:out value="${room.medicalTeam}" />
                    </td>

                    <td>
                        <spring:url value="/rooms/{roomId}" var="roomUrl">
                            <spring:param name="roomId" value="${room.id}" />
                        </spring:url>
                        <a href="${fn:escapeXml(roomUrl)}">Show</a>

                        <spring:url value="/rooms/delete/{roomId}" var="roomUrl">
                            <spring:param name="roomId" value="${room.id}" />
                        </spring:url>
                        <a href="${fn:escapeXml(roomUrl)}">Delete</a>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <sec:authorize access="hasAuthority('admin')">
            <a class="btn btn-default" href='<spring:url value="/rooms/new" htmlEscape="true"/>'>Add Room</a>
        </sec:authorize>
</petclinic:layout>