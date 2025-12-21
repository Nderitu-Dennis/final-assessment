<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-dark">

<div class="container">
    <div class="row justify-content-center align-items-center vh-100">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body">

                    <h4 class="text-center mb-4">Employee System Login</h4>

                    <div class="mb-3">
                        <label class="form-label">Username</label>
                        <input type="text" id="username" class="form-control">
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Password</label>
                        <input type="password" id="password" class="form-control">
                    </div>

                    <div class="d-grid">
                        <button class="btn btn-primary" onclick="login()">Login</button>
                    </div>

                    <div id="error" class="text-danger text-center mt-3"></div>

                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function login() {
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        fetch("/api/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        })
        .then(res => {
            if (!res.ok) throw new Error();
            return res.json();
        })
        .then(data => {
        // store jwt token aftr login
            localStorage.setItem("jwtToken", data.token);
            window.location.href = "/dashboard";
        })
        .catch(() => {
            document.getElementById("error").innerText = "Invalid username or password";
        });
    }
</script>

</body>
</html>
