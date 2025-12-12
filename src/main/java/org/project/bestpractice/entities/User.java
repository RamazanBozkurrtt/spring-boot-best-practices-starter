package org.project.bestpractice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.project.bestpractice.jwt.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<UUID> {

    @Column(unique = false, nullable = false,name = "firstname")
    private String firstname;

    @Column(unique = false,nullable = false,name = "lastname")
    private String lastname;


    @Column(name = "email",length = 45, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 60,nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Token> tokens;

    private boolean active;

    private boolean locked;

}
