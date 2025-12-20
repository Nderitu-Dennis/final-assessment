package tech.csm.final_assmnt.controller;


import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.csm.final_assmnt.model.Employee;
import tech.csm.final_assmnt.model.User;
import tech.csm.final_assmnt.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
//admin only
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees() {

        return employeeService.getAllEmployees();
    }

    @PostMapping
    public Employee createEmployee(
            @RequestBody Employee employee,
            HttpServletRequest request) {

        Claims claims = (Claims) request.getAttribute("claims");
        Integer userId = (Integer) claims.get("userId");

        return employeeService.saveEmployee(employee, userId);
    }

}

