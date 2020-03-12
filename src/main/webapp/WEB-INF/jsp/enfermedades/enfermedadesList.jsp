<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="enfermedades">
    <h2>Enfermedades</h2>

    <table id="enfermedadesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Sintomas</th>
            <th style="width: 200px;">Gravedad</th>
            <th>Cura</th>
            <th>Pets</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${enfermedades}" var="enfermedad">
            <tr>
               
               
                <td>
                   
                <c:out value="${enfermedad.sintomas}"/>
                </td>
                <td>
                    <c:out value="${enfermedad.gravedad}"/>
                </td>
                <td>
                    <c:out value="${enfermedad.cura}"/>
                </td>

                <td>
                    <c:out value="${enfermedad.pets}"/>
                </td>
           
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
