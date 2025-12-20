package tech.csm.final_assmnt.service;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.csm.final_assmnt.model.Project;
import tech.csm.final_assmnt.model.User;
import tech.csm.final_assmnt.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Project saveProject(Project project, Integer createdByUserId) {

        User userRef = entityManager.getReference(User.class, createdByUserId);
        project.setCreatedBy(userRef);
        return projectRepository.save(project);
    }


    public Project getProjectById(Integer id) throws Exception {
        return projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project not found"));
    }
}

