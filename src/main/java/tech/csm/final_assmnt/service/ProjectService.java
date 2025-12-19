package tech.csm.final_assmnt.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import tech.csm.final_assmnt.model.Project;
import tech.csm.final_assmnt.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project getProjectById(Integer id) throws Exception {
        return projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project not found"));
    }
}

