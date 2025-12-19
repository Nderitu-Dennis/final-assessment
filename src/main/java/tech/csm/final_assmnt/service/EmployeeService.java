package tech.csm.final_assmnt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.csm.final_assmnt.model.Employee;
import tech.csm.final_assmnt.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Integer id) throws Exception {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new Exception("Employee not found"));
    }
}

