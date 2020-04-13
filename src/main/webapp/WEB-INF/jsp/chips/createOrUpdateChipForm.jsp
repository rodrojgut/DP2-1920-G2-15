<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    
    <jsp:body>
        <h2>
            <c:if test="${pet['new']}">New </c:if> Chip
        </h2>
        <form:form modelAttribute="chip" class="form-horizontal">
            <input type="hidden" name="pet_id" value="${petId}"/>
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Serial Number" name="serialNumber"/>
                <petclinic:inputField label="Model" name="model"/>
                <petclinic:inputField label="Geolocatable" name="geolocatable"/>

				<%-- 
                <label class="col-sm-2 control-label">Geolocatable</label>
                <input type="checkbox" name="geolocatable" path="geolocatable"/>
                --%>
                
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${chip['new']}">
                            <button class="btn btn-default" type="submit">Add Chip</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Chip</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>
