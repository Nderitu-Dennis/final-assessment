<!DOCTYPE html>
<html>
<head>
<title>Assign Project</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4 bg-dark" >

<h3>Assign Employee to Project</h3>
<!--manager-->

<select id="emp" class="form-select mb-2"></select>
<select id="proj" class="form-select mb-2"></select>

<button class="btn btn-warning" onclick="assign()">Assign</button>

<script>
const token = localStorage.getItem("jwtToken");

fetch("/api/employees",{headers:{Authorization:"Bearer "+token}})
.then(r=>r.json())
.then(d=>{
 document.getElementById("emp").innerHTML =
   d.map(e=>`<option value="${e.employeeId}">${e.name}</option>`).join("");
});

fetch("/api/projects",{headers:{Authorization:"Bearer "+token}})
.then(r=>r.json())
.then(d=>{
 document.getElementById("proj").innerHTML =
   d.map(p=>`<option value="${p.projectId}">${p.projectName}</option>`).join("");
});

function assign(){
 fetch("/api/assign",{
   method:"POST",
   headers:{
     "Authorization":"Bearer "+token,
     "Content-Type":"application/json"
   },
   body:JSON.stringify({
     employeeId:emp.value,
     projectId:proj.value
   })
 }).then(()=>alert("Assigned successfully"));
}
</script>

</body>
</html>
