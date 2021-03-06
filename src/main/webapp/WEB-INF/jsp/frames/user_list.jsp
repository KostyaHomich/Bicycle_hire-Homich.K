<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="epam.project.command.CommandType" %>
<%@ taglib prefix="lang" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="table table-striped jambo_table bulk_action">
    <thead>

    <tr class="headings">
        <th class="column-title"><fmt:message key="user.login"/></th>
        <th class="column-title"><fmt:message key="user.email"/></th>
        <th class="column-title"><fmt:message key="user.first_name"/></th>
        <th class="column-title"><fmt:message key="user.last_name"/></th>
        <th class="column-title"><fmt:message key="user.status"/></th>
        <th class="column-title no-link last"><span class="nobr"><fmt:message key="page.button.action"/></span></th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.users}" var="user">
        <tr class="even pointer">
            <td class=" ">${user.getLogin()}</td>
            <td class=" ">${user.getEmail()}</td>
            <td class=" ">${user.getFirstName()}</td>
            <td class=" ">${user.getLastName()}</td>
            <td class=" ">${user.getStatus()}</td>
            <td>
                <form style="display: inline-block;"
                      action="${pageContext.request.contextPath}/user_details" method="post">
                    <input type="hidden" name="userId" value="${user.getId()}">
                    <input type="hidden" name="command" value="${CommandType.SHOW_USER_DETAILS}">
                    <input  type="submit" value="Show">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

