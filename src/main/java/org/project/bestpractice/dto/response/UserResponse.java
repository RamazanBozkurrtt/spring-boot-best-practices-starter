package org.project.bestpractice.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserResponse {

    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDateTime createdAt;

    private boolean active;

    private boolean locked;

}
