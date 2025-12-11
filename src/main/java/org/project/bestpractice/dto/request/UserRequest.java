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

    @NotNull(message = "İsim adı boş olamaz")
    @NotEmpty(message = "İsim adı boş olamaz")
    @Size(min = 2, max = 20, message = "İsim adı 2-35 karakter olmalı")
    private String firstname;


    @NotNull(message = "Soyisim adı boş olamaz")
    @NotEmpty(message = "Soyisim adı boş olamaz")
    @Size(min = 2, max = 20, message = "Soyisim adı 2-20 karakter olmalı")
    private String lastname;

    @NotNull(message = "Şifre boş olamaz")
    @NotEmpty(message = "Şifre boş olamaz")
    @Size(min = 6, max = 20, message = "Şifre en az 6 karakter olmalı")
    private String password;

    @Email(message = "Geçerli bir e-posta formatı girin")
    @NotEmpty
    @Size(max = 80)
    private String email;
}
