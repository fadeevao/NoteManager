
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="parent.jsp"/>
<html>
<head>
<title>Note list</title>
</head>
<body>
	<jsp:include page="logout.jsp" />

	<h2>All about your notebook, ${user.getUsername()}</h2>
	<p>Notes added: ${notes.size()}</p>

	<form action="/NoteManager/addNote">
		<input type="submit" value="Add a note">
	</form>


	<form method="post" action="/NoteManager/deleteSelectedNotes"
		class="form">
		<table>
			<c:forEach items="${notes}" var="note" varStatus="status">
				<tr>
					<td><a href="/NoteManager/notes/${note.getName()}"><c:out
								value="${note.getName()}" /></a></td>
					<td><input type="checkbox" name="selected"
						value="${note.getId()}"></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${notes.size() > 0}">
			<form action="/NoteManager/deleteSelectedNotes">
				<input type="submit" value="Delete selected notes">
			</form>
		</c:if>
	</form>

	<c:if test="${notes.size() > 0}">
		<form action="/NoteManager/delete">
			<input type="submit" value="Delete all notes">
		</form>
	</c:if>

</body>
</html>