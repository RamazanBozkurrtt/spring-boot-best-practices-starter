package org.project.bestpractice.dto.request;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequest {

    @NotNull(message = "Kullanıcı adı boş olamaz")
    @NotEmpty
    @Size(min = 3, max = 35, message = "Kullanıcı adı 3-35 karakter olmalı")
    private String username;

    @NotNull(message = "Şifre boş olamaz")
    @NotEmpty
    @Size(min = 6, max = 20, message = "Şifre en az 6 karakter olmalı")
    private String password;

    @Email(message = "Geçerli bir e-posta formatı girin")
    @NotEmpty
    @Size(max = 80)
    private String email;
}
