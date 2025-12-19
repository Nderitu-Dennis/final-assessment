package tech.csm.final_assmnt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.csm.final_assmnt.model.EmployeeProject;
import tech.csm.final_assmnt.repository.EmployeeProjectRepository;

import java.util.List;

@Service
public class EmployeeProjectService {

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    public EmployeeProject assign(EmployeeProject ep) {
        return employeeProjectRepository.save(ep);
    }

    public List<EmployeeProject> getProjectsForEmployee(Integer employeeId) {
        return employeeProjectRepository.findByEmployeeId(employeeId);
    }
}

