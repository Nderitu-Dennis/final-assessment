package tech.csm.final_assmnt.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.csm.final_assmnt.model.Project;
import tech.csm.final_assmnt.model.User;
import tech.csm.final_assmnt.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    //admin

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getProjects(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return projectService.getAllProjects();
    }

    @PostMapping
    public Project createProject(@RequestBody Project project,
                                 HttpServletRequest request) {

        Claims claims = (Claims) request.getAttribute("claims");
        Integer userId = (Integer) claims.get("userId");
        return projectService.saveProject(project, userId);
    }

}

