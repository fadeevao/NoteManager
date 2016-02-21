<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="parent.jsp" />
<html>
<head>
<title>Add a new note</title>
</head>
<body>
	<div class="dark-matter">
		<jsp:include page="logout.jsp" />
		<h2 style="margin-top: 30px">Add a new note:</h2>
		<form:form method="POST" modelAttribute="note"
			action="/NoteManager/addNote">

			<form:label path="name">Title</form:label>
			<form:input path="name" />
			<form:errors path="name" class="error-message"></form:errors>

			<form:label path="content">Content</form:label>
			<form:textarea path="content" />
			<form:errors path="content" class="error-message"></form:errors>
			<br />

			<input type="submit" value="Submit" class="button" />
			<a href="notes" class="button">Go Back</a>

		</form:form>
	</div>
</body>
</html>