package tech.csm.final_assmnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.csm.final_assmnt.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
