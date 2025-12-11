package org.project.bestpractice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "token",unique = true,nullable = false)
    public String token;

    public boolean expired;
    public boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

}
