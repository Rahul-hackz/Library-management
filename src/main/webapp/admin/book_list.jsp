<%@ page import="com.library.dao.BookDAO" %>
<%@ page import="com.library.model.Book" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Books</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-3">
    <h2>Books</h2>
    <a href="add_book.jsp" class="btn btn-success">Add Book</a>
    <table class="table mt-3">
        <thead><tr><th>Title</th><th>Author</th><th>Category</th><th>Qty</th><th>Action</th></tr></thead>
        <tbody>
        <%
            BookDAO dao = new BookDAO();
            try{
                for(Book b : dao.getAll()){
        %>
            <tr>
                <td><%=b.getTitle()%></td>
                <td><%=b.getAuthor()%></td>
                <td><%=b.getCategory()%></td>
                <td><%=b.getQuantity()%></td>
                <td>
                    <form style="display:inline" action="../BookServlet" method="post">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="<%=b.getBookId()%>"/>
                        <button class="btn btn-danger btn-sm" onclick="return confirm('Delete book?')">Delete</button>
                    </form>
                </td>
            </tr>
        <%
                }
            }catch(Exception e){ out.println(e); }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
