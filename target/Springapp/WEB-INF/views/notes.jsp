
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<html>
<head>
    <title>Note List</title>
</head>
<body>

<h2>Note Information</h2>
Notes added:  ${notes.size()}

 <form action="/NoteManager/addNote">   
    <input type="submit" value="Add another note">
 </form>
<table>
  <c:forEach items="${notes}" var="note"  varStatus="status">
    <tr>
      <td><c:out value="${note.getName()}" /></td>
      <td><c:out value="${note.getSize()}" /></td>
      <td><c:out value="${note.getCreatedAt()}" /></td>
      <td><input TYPE=checkbox id="delete" VALUE="${note.toDelete}"> delete</td> 
    </tr>
  </c:forEach> 
</table>


<c:if test="${notes.size() > 0}">
 <button type="submit">Delete selected notes</button>
</c:if>


</body>
</html>