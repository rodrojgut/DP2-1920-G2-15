<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="bookings">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fecha").datepicker({dateFormat: 'yy-mm-dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>
            <c:if test="${booking['new']}">New </c:if> Booking
        </h2>
        <form:form modelAttribute="booking" class="form-horizontal">
            <div class="form-group has-feedback">
                
                <label>Date</label>
               <input type="datetime" name="fecha" id="fecha" class = "form-control" pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))"/>
        
                <br>
                <label>Pet</label> 
                <select id="pet" name = "petId" class = "form-control">
                 <c:forEach items ="${pets}" var="pet">
                    <option value ="${pet.id}">${pet.name}</option>
                 </c:forEach>
                </select>
                <br>
                <label>Vet</label> 
                <select id="vet" name = "vetId" class = "form-control">
                 <c:forEach items ="${vets}" var="vet">
                    <option value ="${vet.id}">${vet.firstName}</option>
                 </c:forEach>
                </select>
                <br>
                <label>Room</label> 
                <select id="room" name = "roomId" class = "form-control">
                 <c:forEach items ="${rooms}" var="room">
                    <option value ="${room.id}">${room.id}</option>
                 </c:forEach>
                </select>

                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${booking['new']}">
                            <button class="btn btn-default" type="submit">Add Booking</button>
                        </c:when>

                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
