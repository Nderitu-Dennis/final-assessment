package tech.csm.final_assmnt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "employee_projects")
@NoArgsConstructor
@IdClass(EmployeeProjectId.class)
public class EmployeeProject {

    @Id
    @Column(name="employee_id")
    private Integer employeeId;

    @Id
    @Column(name="project_id")
    private Integer projectId;

    @Column(name="assigned_date")
    private LocalDate assignedDate;
}

