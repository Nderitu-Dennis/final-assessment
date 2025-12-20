package tech.csm.final_assmnt.service;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.csm.final_assmnt.model.Employee;
import tech.csm.final_assmnt.model.User;
import tech.csm.final_assmnt.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Employee saveEmployee(Employee employee, Integer createdByUserId) {
        User userRef = entityManager.getReference(User.class, createdByUserId);
        employee.setCreatedBy(userRef);

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) throws Exception {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new Exception("Employee not found"));
    }
}

