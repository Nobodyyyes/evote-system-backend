package esmukanov.evote_system.hellgate.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Фамилия не может быть пустым")
        @Size(max = 255, message = "Фамилия не может быть длиннее 255 символов")
        String firstname,

        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 255, message = "Имя не может быть длиннее 255 символов")
        String name,

        @NotBlank(message = "Username не может быть пустым")
        @Size(min = 3, max = 64, message = "Username должен быть от 3 до 64 символов")
        String username,

        @NotBlank(message = "Email не может быть пустым")
        @Size(max = 255, message = "Email не может быть длиннее 255 символов")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6, max = 255, message = "Пароль должен быть минимум 6 символов")
        String password,

        @NotBlank(message = "Подтверждение пароля не может быть пустым")
        String confirmPassword
) {
}
