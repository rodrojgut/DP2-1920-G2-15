<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="opinions">
    <h2>Opinions</h2>

    <table id="opinionsTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Vet</th>
            <th style="width: 50px;">Puntuation</th>
            <th style="width: 100px;">Date</th>
            <th style="width: 500px">Commentary</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${opinions}" var="opinion">
            <tr>
                <td>
                    <c:out value="${opinion.vet.firstName} ${opinion.vet.lastName}"/>
                </td>
                <td>
                    <c:out value="${opinion.puntuation}"/>
                </td>
                <td>
                    <c:out value="${opinion.date}"/>
                </td>
                <td>
                    <c:out value="${opinion.comentary}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
