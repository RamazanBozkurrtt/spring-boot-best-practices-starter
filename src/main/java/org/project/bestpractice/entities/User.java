package org.project.bestpractice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "_user")
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(
        name = "id",
        column = @Column(name = "user_id", insertable = false, updatable = false)
)
public class User extends BaseEntity<UUID> {

    @Column(unique = false, nullable = false,name = "firstname")
    private String firstname;

    @Column(unique = false,nullable = false,name = "lastname")
    private String lastname;


    @Column(name = "email",length = 45, nullable = false, unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<Role> user_roles = new HashSet<Role>();

    private boolean active;

    private boolean locked;

}
