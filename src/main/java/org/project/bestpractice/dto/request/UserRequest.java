package org.project.bestpractice.dto.request;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {

    private UUID id;

    @Size(min = 1, max = 35)
    @NotEmpty
    @NotNull
    private String username;

    @Size(min = 1, max = 20)
    @NotEmpty
    @NotNull
    private String password;

    @Email
    @Size(min = 1, max = 80)
    private String email;

    @NotNull
    @NotEmpty
    private LocalDateTime createdAt;

    @NotNull
    @NotEmpty
    private LocalDateTime updatedAt;
}
