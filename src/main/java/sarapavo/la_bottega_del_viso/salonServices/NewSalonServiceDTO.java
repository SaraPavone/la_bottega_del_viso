package sarapavo.la_bottega_del_viso.salonServices;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewSalonServiceDTO(
        @NotBlank(message = "Il titolo é obbligatorio")
        String title,
        @NotBlank(message = "La descrizione è obbligatoria")
        String description,
        @NotNull(message = "La durata è obbligatoria")
        String duration,
        @NotNull(message = "Il prezzo è obbligatorio")
        Double price
) {
}
