<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="rooms">

    <h2>Room Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Floor</th>
            <td><c:out value="${room.floor}"/></td>
        </tr>
        <tr>
            <th>Team</th>
            <td><c:out value="${room.medicalTeam}"/></td>
        </tr>
    </table>
</petclinic:layout>


