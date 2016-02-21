
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="parent.jsp" />
<html>
<head>
<title>Note list</title>
</head>
<body >
	<div class="dark-matter">
	<jsp:include page="logout.jsp" />

	<h2 style="margin-top: 30px; font-size: 40px;">All about your notebook, ${user.getUsername()}!</h2>
	<p id="numberOfNotes">Currently you've got <b>${notes.size()}</b> notes in your notebook. Why not add more?</p>


	<a href="addNote" class="button">Add a new note</a>


	<form method="post" action="/NoteManager/deleteSelectedNotes"
		class="form">
		<div id="wrap">
			<c:forEach items="${notes}" var="note" varStatus="status">


				<div class="box">
					<div class="innerContent">
						<a class="noteInfo" href="/NoteManager/notes/${note.getName()}"><c:out
								value="${note.getName()}" /></a>
						<div class="roundedTwo">
							<input id="roundedTwo" type="checkbox" name="selected"
								value="${note.getId()}">
						</div>
					</div>
				</div>


			</c:forEach>
		</div>

		<c:if test="${notes.size() > 0}">
		
			<form action="/NoteManager/deleteSelectedNotes" >
				<input class="button" type="submit" style="margin-top: 30px" value="Delete selected notes">
			</form>
		</c:if>
	</form>

	<c:if test="${notes.size() > 0}">
		<form action="/NoteManager/delete" >
			<input type="submit" class="button" style="margin-top: 10px" value="Delete all notes">
		</form>
	</c:if>
</div>
</body>
</html>