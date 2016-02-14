<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="resources/style.css"/>
    <title>Add a new note</title>
</head>
<body>
<jsp:include page="logout.jsp" />
<h2>Note Information</h2>
<form:form method="POST" modelAttribute="note" action="/NoteManager/addNote">
   <table>
    <tr>
        <td><form:label path="name">Name</form:label></td>
        <td><form:input path="name" /></td>
        <form:errors path="name"></form:errors>
    </tr>
    <tr>
        <td><form:label path="content">Content</form:label></td>
        <td><form:textarea path="content" /></td>
        <form:errors path="content"></form:errors>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table>  
</form:form>
</body>
</html>