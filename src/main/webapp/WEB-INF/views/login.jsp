<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="parent.jsp"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>

<body>
	<form:form id="loginForm" method="post" action="login"
		modelAttribute="loginBean" class="dark-matter">
		<c:if test="${not empty message}">
			<p class="error-message">Invalid credentials! Please double check your details and try again.</p>
		</c:if>

		<form:label path="username">Enter your name</form:label>
		<form:input id="username" name="username" path="username" placeholder="Your name"/>
		<br>
		<form:label path="password">Please enter your password</form:label>
		<form:password id="password" name="password" path="password" placeholder="Your password"/>
		<br>
		<input type="submit" value="Login" class="button"/>
		<a href="home" class="button" >Go Back</a>
	</form:form>
</body>
</html>