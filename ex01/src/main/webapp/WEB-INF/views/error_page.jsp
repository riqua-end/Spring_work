<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2>error_page.jsp</h2>

<h4>
	<%-- model객체에 설정된 속성명 exception --%>
	<c:out value="${exception.getMessage()}"></c:out>
</h4>

<ul>
	<c:forEach items="${exception.getStackTrace() }" var="stack">
		<li><c:out value="${stack}"></c:out></li>
	</c:forEach>
</ul>
</body>
</html>