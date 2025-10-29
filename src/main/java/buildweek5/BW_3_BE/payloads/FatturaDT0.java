package buildweek5.BW_3_BE.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FatturaDT0(
        @NotBlank(message = "Il numero fattura è obbligatorio")
        String numero,
        @NotNull(message = "La data è obbligatoria")
        LocalDate data,
        @NotNull(message = "L'importo è obbligatorio")
        @Positive(message = "L'importo deve essere positivo")
        BigDecimal importo,
        @NotNull(message = "Il cliente è obbligatorio")
        Long clienteId,
        @NotNull(message = "Lo stato fattura è obbligatorio")
        Long statoFatturaId
) {
}
