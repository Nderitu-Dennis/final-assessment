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
        System.out.println("##Claimns$$ " + claims);
        Integer userId = (Integer) claims.get("userId");

        System.out.println("user id --" + userId);

        List<EmployeeProject> projects = employeeProjectService.getProjectsForEmployee(userId);
        System.out.println("***printing employee projects");

        if (!projects.isEmpty()) {
            for (EmployeeProject emp : projects) {
                System.out.println(emp);
            }
        } else {
            System.out.println("emp is empty " );
        }


        return projects;
    }
}

