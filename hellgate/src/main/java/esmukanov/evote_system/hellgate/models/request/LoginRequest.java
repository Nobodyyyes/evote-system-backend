package esmukanov.evote_system.hellgate.models.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Username не может быть пустым")
        String username,

        @NotBlank(message = "Пароль не может быть пустым")
        String password
) {
}
