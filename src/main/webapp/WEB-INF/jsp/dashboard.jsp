<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark">

<nav class="navbar navbar-dark bg-secondary px-3">
    <span class="navbar-brand">Employee Management</span>
    <button class="btn btn-outline-light btn-sm" onclick="logout()">Logout</button>
</nav>

<div class="container mt-4" id="menu"></div>

<script>
    const token = localStorage.getItem("jwtToken");
    if (!token) location.href = "/login";

    const role = JSON.parse(atob(token.split('.')[1])).role;
    const menu = document.getElementById("menu");

    if (role === "ROLE_ADMIN") {
        menu.innerHTML = `
          <a href="/employees" class="btn btn-primary me-2">Employees</a>
          <a href="/projects" class="btn btn-secondary">Projects</a>`;
    }

    if (role === "ROLE_MANAGER") {
        menu.innerHTML = `<a href="/assign" class="btn btn-warning">Assign Project</a>`;
    }

    if (role === "ROLE_EMPLOYEE") {
        menu.innerHTML = `<a href="/my-projects" class="btn btn-info">My Projects</a>`;
    }

    function logout() {
        localStorage.removeItem("jwtToken");
        location.href = "/login";
    }
</script>

</body>
</html>
