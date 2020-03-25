<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="diseases">
    <h2>
        <c:if test="${disease['new']}">New </c:if> disease
    </h2>
    <form:form modelAttribute="disease" class="form-horizontal" id="add-disease-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="symptoms" name="symptoms"/>
            <petclinic:inputField label="severity" name="severity"/>
            <petclinic:inputField label="cure" name="cure"/>
             
            
             <select name="petId">
             	<c:forEach items="${pets }" var="petopt">    
             		<option value="${petopt.id}" label="${petopt.name}">${petopt.name}</option>
             	</c:forEach>
             </select> 
            
         
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
        <!--        <c:choose>
                    <c:when test="${disease['new']}">
                        <button class="btn btn-default" type="submit">Add disease</button>
                    </c:when>
                   <c:otherwise>
                        <button class="btn btn-default" type="submit">Update disease</button>
                    </c:otherwise>
                </c:choose>
            </div>-->
                <input type="hidden" name="diseaseId" value="${disease.id}"/>
                <button class="btn btn-default" type="submit">Save disease</button>
            </div>
        </div>
    
    
     
     
    </form:form>


 
</petclinic:layout>
