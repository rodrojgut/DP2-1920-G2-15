<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="pets">
    <jsp:body>
        <h2>
            <c:if test="${pet['new']}">New </c:if> Disease
        </h2>
        <form:form modelAttribute="disease" class="form-horizontal">
            <input type="hidden" name="pet_id" value="${petId}"/>
            <div class="form-group has-feedback">
                
                <petclinic:inputField label="Symptoms" name="symptoms"/>
                <petclinic:inputField label="Severity" name="severity"/>
				<petclinic:inputField label="Cure" name="cure"/>

            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${disease['new']}">
                            <button class="btn btn-default" type="submit">Add Disease</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit">Update Disease</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form:form>
    </jsp:body>
</petclinic:layout>