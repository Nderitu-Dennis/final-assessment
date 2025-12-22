package tech.csm.final_assmnt.model;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode // for Hibernate to compare keys

public class EmployeeProjectId implements Serializable {
    private Integer employeeId;
    private Integer projectId;
}
