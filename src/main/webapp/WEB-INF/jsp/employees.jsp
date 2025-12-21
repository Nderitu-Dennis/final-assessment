<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>Employees</title>
    <!--bootstrap cdn-->
    <link rel="stylesheet"
    	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
    	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
    	crossorigin="anonymous">
</head>

<body class="container mt-4 bg-dark text-white">

<div class="d-flex justify-content-between align-items-center mb-4">
    <h3>Employees</h3>
    <a href="/dashboard" class="btn btn-secondary">Back to Dashboard</a>
</div>

<div id="error" class="alert alert-danger d-none"></div>

<table class="table table-bordered table-striped mt-3">
    <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Department ID</th>
        </tr>
    </thead>

    <tbody id="employeesTable">
    </tbody>
</table>

<!--Using jQuery for the AJAX call -->

<script src="https://code.jquery.com/jquery-2.2.4.js"
		integrity="sha256-iT6Q9iMJYuQiMWNd9lDyBUStIq/8PuOW33aOqmvFpqI="
		crossorigin="anonymous">
</script>

<!--making ajax call -->

<Script>
$(document).ready(function() {

  var token = localStorage.getItem("jwtToken");

    if (!token) {
        console.error("No JWT token found, redirecting to login.");
        window.location.href = "/login";
        return;
    }

    $.ajax({
        url: "/api/employees", // The URL of your Spring Boot endpoint
        type: "GET",
        dataType: "json", // Expecting JSON data
          headers: {
                    "Authorization": "Bearer " + token // Add the Authorization header
                },

        success: function(data) {
            let tableBody = $("#employeesTable");
             tableBody.empty(); //clear any existing data in table
            // Iterate over the list of data received
            $.each(data, function(index, emp) {
                let row = "<tr>";
                row += "<td>" + emp.employeeId + "</td>"; // Access JSON fields by name
                row += "<td>" + emp.name+ "</td>";
                row += "<td>" + emp.email+ "</td>";
                row += "<td>" + emp.departmentId+ "</td>";
                row += "</tr>";
                tableBody.append(row); // Append the new row to the table body
            });
        },
        error: function(error) {
            alert("Error fetching data: " + error.responseText);
        }
    });
});

</script>
</body>
</html>