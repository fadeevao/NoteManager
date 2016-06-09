<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="parent.jsp" />

<html>
<head>
<title>Home</title>
</head>
<body>
	<div class="dark-matter" style="text-align: center">
		<h2>Hey, log into your account to add
			notes</h2>
		
			<a href="login" class="button">Login</a>
		
		<h2>Alternatively, register to start
			adding notes immediately!</h2>
		
			<a href="register" class="button">Register</a>
	
		<!-- https://upload.wikimedia.org/wikipedia/commons/d/d8/Language_bulletin_board_KSU.jpg -->
		<img src="resources/images/bulletin_board.jpg" alt="Bulletin board">
	</div>
</body>
</html>
