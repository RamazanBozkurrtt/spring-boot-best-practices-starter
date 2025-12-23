package org.project.bestpractice.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "İsim boş olamaz")
        @Size(min = 2, max = 50, message = "İsim 2-50 karakter arasında olmalıdır")
        String firstname,

        @NotBlank(message = "Soyisim boş olamaz")
        @Size(min = 2, max = 50, message = "Soyisim 2-50 karakter arasında olmalıdır")
        String lastname,

        @NotBlank(message = "Email boş olamaz")
        @Email(message = "Geçerli bir email adresi giriniz")
        String email,

        @NotBlank(message = "Şifre boş olamaz")
        @Size(min = 6, max = 100, message = "Şifre en az 6 karakter olmalıdır")
        String password
) { }
