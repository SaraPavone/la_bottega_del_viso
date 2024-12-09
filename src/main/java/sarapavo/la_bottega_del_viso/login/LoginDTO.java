package sarapavo.la_bottega_del_viso.login;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull(message = "L'email é obbligatoria!")
        String email,
        @NotNull(message = "La password é obbligatoria!")
        String password) {
}
