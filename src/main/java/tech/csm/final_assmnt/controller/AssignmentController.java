package tech.csm.final_assmnt.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.csm.final_assmnt.model.EmployeeProject;
import tech.csm.final_assmnt.service.EmployeeProjectService;

import java.time.LocalDate;
import java.util.Map;


@RestController
@RequestMapping("/api/assign")
public class AssignmentController {

//only managers will hit this
    @Autowired
    private EmployeeProjectService employeeProjectService;

    @PostMapping
    public EmployeeProject assignEmployee(@RequestBody Map<String, Integer> payload, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        Integer employeeId = payload.get("employeeId");
        Integer projectId = payload.get("projectId");

        EmployeeProject ep = new EmployeeProject();
        ep.setEmployeeId(employeeId);
        ep.setProjectId(projectId);
        ep.setAssignedDate(LocalDate.now());

        return employeeProjectService.assign(ep);
    }
}

