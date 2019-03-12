<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<c:choose>

    <c:when test="${bicycle!=null}">
        <jsp:include page="frames/bicycle_details.jsp"/>
    </c:when>

    <c:when test="${user!=null}">
        <jsp:include page="frames/user_details.jsp"/>
    </c:when>

    <c:when test="${order!=null}">
        <jsp:include page="frames/order_details.jsp"/>
    </c:when>

    <c:when test="${point_hire!=null}">
        <jsp:include page="frames/point_hire_details.jsp"/>
    </c:when>
</c:choose>

</body>
</html>