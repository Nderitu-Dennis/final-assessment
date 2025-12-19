package tech.csm.final_assmnt.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    private String username;

    @Column(name="password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="is_active")
    private Boolean isActive = true;

    public User(Integer userId) {
    }


    public enum Role {
        ROLE_ADMIN,
        ROLE_MANAGER,
        ROLE_EMPLOYEE
    }
}

