package tech.csm.final_assmnt.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("******-login page being loaded...");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/employees")
    public String employees() {
        return "employees";
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/assign")
    public String assign() {
        return "assign";
    }

    @GetMapping("/my-projects")
    public String myProjects() {
        return "my-projects";
    }
}

