<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>Assign Project</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4 bg-dark text-white">

    <h3>Assign Project</h3>
    <div class="card bg-secondary p-4 mt-3">
        <form id="assignForm">

         <div class="row">

          <div class="form-group col-6">
                         <label>Select Employee</label>
                         <select id="employeeSelect" class="form-control" required>
                             <option value="">-- Choose Employee --</option>
                         </select>
                     </div>

                    <div class="form-group col-6">
                                  <label>Select Project</label>
                                  <select id="projectSelect" class="form-control" required>
                                      <option value="">-- Choose Project --</option>
                                  </select>
                              </div>
         </div>




            <button type="submit" class="btn btn-primary btn-block">Assign Now</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-2.2.4.js"></script>

    <script>
    $(document).ready(function() {
        var token = localStorage.getItem("jwtToken");
        var headers = { "Authorization": "Bearer " + token };

       // Fetch Employees
       $.ajax({
           url: "/api/employees",
           type: "GET",
           headers: headers,
           success: function(employees) {
               console.log("Employees received:", employees);

               var employeeSelect = $("#employeeSelect");
                employeeSelect.empty().append("<option value='0'>-select employee-</option>");
               $(employees).each(function(i, emp) {
               employeeSelect.append("<option value='" + emp.employeeId + "'>" + emp.name + "</option>");
               });
           }
       });

       // Fetch Projects
       $.ajax({
           url: "/api/projects",
           type: "GET",
           headers: headers,
           success: function(projects) {
               console.log("Projects received:", projects);

               var projectSelect = $("#projectSelect");

               projectSelect.find("option").remove(); // Clear existing
               projectSelect.append("<option value='0'>-select project-</option>");

               $(projects).each(function(i, prj) {
                   console.log(prj);
                   projectSelect.append("<option value='" + prj.projectId + "'>" + prj.projectName + "</option>");
               });
           }
       });

        // Submit  assignment
        $("#assignForm").submit(function(e) {
            e.preventDefault();

            var payload = {
                employeeId: parseInt($("#employeeSelect").val()),
                projectId: parseInt($("#projectSelect").val())
            };

            $.ajax({
                url: "/api/assign",
                type: "POST",
                contentType: "application/json",
                headers: headers,
                data: JSON.stringify(payload),
                success: function(response) {
                    alert("Success! Project assigned on: " + response.assignedDate);
                    $("#assignForm")[0].reset();
                },
                error: function(xhr) {
                    alert("Error: " + xhr.responseText);
                }
            });
        });
    });
    </script>
</body>
</html>