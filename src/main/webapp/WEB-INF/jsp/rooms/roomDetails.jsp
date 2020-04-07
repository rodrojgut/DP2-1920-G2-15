<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="rooms">

    <h2>Room Information</h2>


    <table class="table table-striped">
      
         <tr>
            <th>Name</th>
            <td><c:out value="${room.name}"/></td>
        </tr>
        <tr>
            <th>Floor</th>
            <td><c:out value="${room.floor}"/></td>
        </tr>
        <tr>
            <th>Medical Team</th>
            <td><c:out value="${room.medicalTeam}"/></td>
        </tr>
        
    </table>
     
     
            <div class="col-sm-offset-2 col-sm-10">
     
             <a class="btn btn-default" href='<spring:url value="/rooms/roomsList" htmlEscape="true"/>'>Back</a>
     
            </div>
        
</petclinic:layout>


