package buildweek5.BW_3_BE.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FatturaDTO {

    @NotBlank(message = "Il numero fattura è obbligatorio")
    private String numero;

    @NotNull(message = "La data è obbligatoria")
    private LocalDate data;

    @NotNull(message = "L'importo è obbligatorio")
    @Positive(message = "L'importo deve essere positivo")
    private BigDecimal importo;

    @NotNull(message = "Il cliente è obbligatorio")
    private Long clienteId;

    @NotNull(message = "Lo stato fattura è obbligatorio")
    private Long statoFatturaId;
}
