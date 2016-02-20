<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="parent.jsp" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration form</title>
</head>
<body>

	<form:form id="registerForm" method="post" action="register"
		modelAttribute="user" class="dark-matter">
		<c:if test="${not empty invalidUsernameMessage}">
			<p  class="error-message">User with such name already exists. Please pick another username.</p>
		</c:if>
		<form:label path="username" class="error_message" >Please enter your username</form:label>
		<form:input id="username" name="username" path="username"
			placeholder="Your username" />
		<form:errors path="username" class="error-message"></form:errors>
		<br>

		<form:label path="password">Please enter your password</form:label>
		<form:password id="password" name="password" path="password"
			placeholder="Your password" />
		<form:errors path="password" class="error-message" ></form:errors>
		<br>
		<input type="submit" value="Submit" class="button" />
		<a href="home" class="button">Go Back</a>
	</form:form>
</body>
</html>