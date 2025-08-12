<%@ page import="com.library.dao.TransactionDAO" %>
<%@ page import="com.library.model.Transaction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<html>
<head>
    <title>My Books</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-3">
    <h2>My Borrowed Books</h2>
    <table class="table">
        <thead><tr><th>Book ID</th><th>Issue Date</th><th>Due Date</th><th>Return Date</th><th>Fine</th><th>Action</th></tr></thead>
        <tbody>
        <%
            TransactionDAO tdao = new TransactionDAO();
            try {
                for (Transaction t : tdao.findByUser(userId)) {
        %>
            <tr>
                <td><%=t.getBookId()%></td>
                <td><%=t.getIssueDate()%></td>
                <td><%=t.getDueDate()%></td>
                <td><%=t.getReturnDate()%></td>
                <td><%=t.getFine()%></td>
                <td>
                    <% if ("ISSUED".equals(t.getStatus())) { %>
                        <form action="../TransactionServlet" method="post" style="display:inline">
                            <input type="hidden" name="action" value="return"/>
                            <input type="hidden" name="transactionId" value="<%=t.getTransactionId()%>"/>
                            <input type="hidden" name="bookId" value="<%=t.getBookId()%>"/>
                            <input type="hidden" name="dueDate" value="<%=t.getDueDate()%>"/>
                            <button class="btn btn-sm btn-success">Return</button>
                        </form>
                    <% } else { %>
                        Returned
                    <% } %>
                </td>
            </tr>
        <%
                }
            } catch (Exception e) { out.println(e); }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
