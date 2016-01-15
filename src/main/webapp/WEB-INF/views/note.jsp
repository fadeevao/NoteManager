<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Note ${note.getName()}</title>
</head>
<body>
	<h1>Note information</h1>
	<h2>Note name:</h2>
	<p>${note.getName()}</p>
	<h2>Note size:</h2>
	<p>${note.getSize()}</p>
	<h2>Note creation date:</h2>
	<p>${note.getFormattedDate(note.getCreatedAt())}</p>
	<h2>Note content:</h2>
	<p>${note.getContent()}</p>
</body>
</html>