package buildweek5.BW_3_BE.payloads;

import jakarta.validation.constraints.NotBlank;

public record StatoFatturaDTO(
        @NotBlank(message = "Lo stato fattura non può essere vuoto")
        String statoFattura
) {
}
