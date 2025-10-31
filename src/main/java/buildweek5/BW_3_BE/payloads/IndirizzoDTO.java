package buildweek5.BW_3_BE.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IndirizzoDTO(
        @NotBlank(message = "La via è obbligatoria")
        String via,
        @NotBlank(message = "Il numero civico è obbligatorio")
        String civico,
        String localita,
        @NotBlank(message = "Il CAP è obbligatorio")
        @Size(min = 5, max = 5, message = "Il CAP deve essere di 5 caratteri")
        String cap,
        @NotNull(message = "Il comune è obbligatorio")
        Long comuneId
) {
}
