package sarapavo.la_bottega_del_viso.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NewReservationDTO(
        @NotBlank(message = "Il nome é obbligatorio")
        String name,
        @NotBlank(message = "Il cognome é obbligatorio")
        String surname,
        @NotBlank(message = "Il nome del servizio é obbligatorio")
        String salonServiceTitle,
        @NotNull(message = "La data e l'ora dell'appuntamento sono obbligatorie")
        LocalDateTime appointmentDateTime
) {
}
