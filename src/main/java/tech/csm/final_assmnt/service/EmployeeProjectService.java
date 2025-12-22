package tech.csm.final_assmnt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.csm.final_assmnt.model.Employee;
import tech.csm.final_assmnt.model.EmployeeProject;
import tech.csm.final_assmnt.repository.EmployeeProjectRepository;
import tech.csm.final_assmnt.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeProjectService {

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeProject assign(EmployeeProject ep) {
        return employeeProjectRepository.save(ep);
    }

     public List<EmployeeProject> getProjectsForEmployee(Integer userId) {
        // Convert the Login ID/USER ID (3) to the Employee ID (10)
        Employee emp = employeeRepository.findByUserId(userId);

        if (emp == null) {
            System.out.println("No Employee record linked to User ID: " + userId);
            return java.util.Collections.emptyList();
        }

        // Use the REAL Employee ID to get the projects
        Integer realId = emp.getEmployeeId();
        System.out.println(" Mapping User " + userId + " to Employee " + realId);
        return employeeProjectRepository.findByEmployeeId(realId);
    }
}

