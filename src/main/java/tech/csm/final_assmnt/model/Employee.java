package tech.csm.final_assmnt.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@AllArgsConstructor

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private Integer employeeId;

    private String name;

    private String email;

    @Column(name="department_id")
    private Integer departmentId;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}
