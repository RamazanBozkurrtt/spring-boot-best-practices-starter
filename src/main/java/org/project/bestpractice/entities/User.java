package org.project.bestpractice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "username",unique = false, length = 30, nullable = false)
    private String username;

    @Column(name = "email",length = 45, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 60,nullable = false)
    private String password;



}
