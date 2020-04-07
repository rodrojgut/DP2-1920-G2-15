<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="opinions">
    <h2>
        <c:if test="${opinion['new']}">New </c:if> Opinion
    </h2>
    <form:form modelAttribute="opinion" class="form-horizontal" id="add-opinion-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label ="Comentary" name="comentary"/>
            <petclinic:inputField label="Puntuation" name="puntuation"/>
            <form:hidden path="user"/>
            <form:hidden  path="vet" />
            <form:hidden path="date" />
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${opinion['new']}">
                        <button class="btn btn-default" type="submit">Add Opinion</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Opinion</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>