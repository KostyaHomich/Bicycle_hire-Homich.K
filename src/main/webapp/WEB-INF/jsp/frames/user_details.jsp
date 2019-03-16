<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="epam.project.command.CommandType" %>
<%@ taglib prefix="lang" tagdir="/WEB-INF/tags" %>

<div class="container">
    <section id="content">

        <form action="${pageContext.request.contextPath}/user_list" method="post">
            <h1>User details</h1>
            <c:out value="${requestScope.error}"/>

            <c:if test="${not empty requestScope.errorsList}">
                <c:forEach var="entry" items="${requestScope.errorsList.getErrors()}">
                    <c:if test="${entry.value.size()>0}">
                        Error:<c:out value="${entry.value}"/>
                    </c:if>
                </c:forEach>
            </c:if>

            <input type="hidden" name="id" value="${requestScope.user.getId()}">

            <input type="hidden" value="${requestScope.user.getLogin()}" name="login" }/>
            <div>
                <input type="text" value="${requestScope.user.getEmail()}" placeholder="Email" id="email" name="email"/>
            </div>
            <div>
                <input type="text" value="${requestScope.user.getFirstName()}" placeholder="First name" id="first_name"
                       name="first_name"/>
            </div>
            <div>
                <input type="text" value="${requestScope.user.getLastName()}" placeholder="Last name" id="last_name"
                       name="last_name"/>
            </div>
            <div>
                <input type="text" value="${requestScope.user.getBalance()}" placeholder="Balance" id="balance" name="balance"/>
            </div>
            <div>
                <input type="text" value="${requestScope.user.getStatus()}" placeholder="Status" id="status" name="status"/>
            </div>

            <div>
                <input  type="submit" value="Update">
            </div>
            <input type="hidden" name="command" value="${CommandType.UPDATE_USER}">

        </form>
        <form action="${pageContext.request.contextPath}/user_details" method="post">

            <div>
                <input type="submit" value="Back">
                <input type="hidden" name="command" value="${CommandType.SHOW_USER_LIST}">
            </div>
        </form>
        <!-- form -->
    </section>
    <!-- content -->
</div>
