package org.project.bestpractice.payload;

import org.project.bestpractice.jwt.Role;

public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password,
        Role role
) { }
