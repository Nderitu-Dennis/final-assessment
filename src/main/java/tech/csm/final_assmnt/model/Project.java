package tech.csm.final_assmnt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private Integer projectId;

    @Column(name="project_name")
    private String projectName;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}

