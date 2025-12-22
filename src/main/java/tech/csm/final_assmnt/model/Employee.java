package tech.csm.final_assmnt.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@NoArgsConstructor
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

    @Column(name = "user_id")
    private Integer userId;

}
