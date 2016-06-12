

<jsp:include page="parent.jsp" />
<html>

<head>
<title>Note ${note.getName()}</title>
</head>
<body>
	<div class="dark-matter">
		<h1>Note information</h1>
		<h2>Note username:</h2>
		<p>${note.getName()}</p>
		<h2>Note size:</h2>
		<p>${note.getSize()}</p>
		<h2>Note creation date:</h2>
		<p>${note.getFormattedDate(note.getCreatedAt())}</p>
		<h2>Note content:</h2>
		<p>${note.getContent()}</p>
	</div>
</body>
</html>