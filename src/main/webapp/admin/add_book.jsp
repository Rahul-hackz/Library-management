<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Book</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Add Book</h2>
    <form action="../BookServlet" method="post">
        <input type="hidden" name="action" value="add"/>
        <div class="mb-3">
            <label class="form-label">Title</label>
            <input name="title" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Author</label>
            <input name="author" class="form-control" />
        </div>
        <div class="mb-3">
            <label class="form-label">Category</label>
            <input name="category" class="form-control" />
        </div>
        <div class="mb-3">
            <label class="form-label">Quantity</label>
            <input name="quantity" type="number" min="1" class="form-control" value="1" />
        </div>
        <button class="btn btn-success">Add</button>
    </form>
</div>
</body>
</html>
