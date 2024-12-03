package sarapavo.la_bottega_del_viso.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import sarapavo.la_bottega_del_viso.user.NewUserDTO;

import java.util.Date;

public record NewCustomerDTO(
        @NotEmpty(message = "Il nome è obbligatoria")
        String name,
        @NotEmpty(message = "Il cognome è obbligatoria")
        String surname,
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email deve essere valida")
        String email,
        @NotEmpty(message = "La password è obbligatoria")
        String password,
        @NotEmpty(message = "La data di nascita é obbligatoria!")
        Date birthDate
) {
    public NewCustomerDTO(NewUserDTO userDTO, Date birthDate) {
        this(userDTO.name(), userDTO.surname(), userDTO.email(), userDTO.password(), birthDate);
    }
}
