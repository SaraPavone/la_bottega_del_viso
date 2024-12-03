package sarapavo.la_bottega_del_viso.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record NewAdminDTO(
        @NotEmpty(message = "Il nome è obbligatoria")
        String name,
        @NotEmpty(message = "Il cognome è obbligatoria")
        String surname,
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email deve essere valida")
        String email,
        @NotEmpty(message = "La password è obbligatoria")
        String password
) {

}
