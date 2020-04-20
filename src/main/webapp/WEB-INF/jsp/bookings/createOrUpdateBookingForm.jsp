<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="bookings">
    
    <jsp:body>
        <h2>
            <c:if test="${booking['new']}">New </c:if> Chip
        </h2>
        <form:form modelAttribute="booking" class="form-horizontal">
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Date" name="date"/>
             

                <label>Owner</label> 
                <select id="onwer" name = "owner" class = "form-control">
                 <c:forEach items ="${owners}" var="owner">
                    <option value ="${owner.firstName}">${owner.firstName}</option>
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
