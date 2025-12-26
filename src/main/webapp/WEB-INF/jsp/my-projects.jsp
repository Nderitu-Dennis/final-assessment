<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>My Projects</title>
<!--bootstrap cdn-->
    <link rel="stylesheet"
    	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
    	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
    	crossorigin="anonymous">

</head>
<body class="container mt-4 bg-dark text-white">

<h3 class="text-white">My Assigned Projects</h3>
<!-- employee -->

<table class="table table-bordered table-striped mt-3">
    <thead class="table-dark">
      <tr>
                <th>Project ID</th>
                <th> Date assigned</th>
            </tr>
    </thead>

    <tbody id="myprojectsTable">
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
 // extracting jwt token
  var token = localStorage.getItem("jwtToken");

    if (!token) {
        console.error("No JWT token found, redirecting to login.");
        window.location.href = "/login";
        return;
    }

    $.ajax({
        url: "/api/projects/view", // The URL of your Spring Boot endpoint
        type: "GET",
        dataType: "json", // Expecting JSON data
          headers: {
                    "Authorization": "Bearer " + token // Add the Authorization header
                },

        success: function(data) {
            let tableBody = $("#myprojectsTable");
             tableBody.empty(); //clear any existing data in table

            // Iterate over the list of data received
            $.each(data, function(index, myprjct) {
            console.log("my-projects", myprjct)
                let row = "<tr>";
                row += "<td>" + myprjct.projectId + "</td>"; // Access JSON fields by name
                row += "<td>" + myprjct.assignedDate + "</td>";

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
