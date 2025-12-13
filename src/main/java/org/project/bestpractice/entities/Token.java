package org.project.bestpractice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.project.bestpractice.jwt.TokenType;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "token",unique = true,nullable = false)
    public String token;

    public boolean expired;
    public boolean revoked;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    public User user;

}
