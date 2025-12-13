package org.project.bestpractice.payload;

public record AuthenticationRequest(
        String email,
        String password
) {
}
