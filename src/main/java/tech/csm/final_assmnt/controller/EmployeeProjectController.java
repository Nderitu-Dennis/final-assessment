package tech.csm.final_assmnt.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.csm.final_assmnt.model.EmployeeProject;
import tech.csm.final_assmnt.service.EmployeeProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects/view")
public class EmployeeProjectController {
//employees see only their prjcts
    @Autowired
    private EmployeeProjectService employeeProjectService;

    @GetMapping
    public List<EmployeeProject> viewAssignedProjects(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("##Claimns$$ "+ claims);
        Integer userId = (Integer) claims.get("userId");
        return employeeProjectService.getProjectsForEmployee(userId);
    }
}

