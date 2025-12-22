package tech.csm.final_assmnt.repository;

import tech.csm.final_assmnt.model.EmployeeProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.csm.final_assmnt.model.EmployeeProjectId;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
    List<EmployeeProject> findByEmployeeId(Integer employeeId);
}
