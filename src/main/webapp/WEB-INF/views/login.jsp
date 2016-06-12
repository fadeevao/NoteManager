<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="parent.jsp"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>

<body>

	<form:form id="loginForm" method="post" action="/login"
		modelAttribute="loginBean" class="dark-matter">
		<c:if test="${param.error}">
			<div class="error-message">Invalid username or password, please try again</div>
		</c:if>
		<br>
		<form:label path="username">Enter your username</form:label>
		<form:input id="username" username="username" path="username" placeholder="Your username"/>
		<br>
		<form:label path="password">Please enter your password</form:label>
		<form:password id="password" username="password" path="password" placeholder="Your password"/>
		<br>
		<input type="submit" value="Login" class="button"/>
		<a href="home" class="button" >Go Back</a>
	</form:form>
</body>
</html>