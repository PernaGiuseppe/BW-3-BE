package buildweek5.BW_3_BE.payloads;

import buildweek5.BW_3_BE.entities.Indirizzo;
import buildweek5.BW_3_BE.entities.Provincia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
public class ClienteFilterPayload{
    @Positive(message = "Il fatturato minimo non può essere negativo")
    private BigDecimal fatturatoMin;
    @Positive(message = "Il fatturato massimo non può essere negativo")
    private BigDecimal fatturatoMax;
    @NotNull(message = "La data inserimento non può essere vuota")
    private LocalDate dataInserimentoInizio;
    @NotNull(message = "La data inserimento fine non può essere vuota")
    private LocalDate dataInserimentoFine;
    private LocalDate dataUltimoContattoInizio;
    private LocalDate dataUltimoContattoFine;
    private String contieneNome;
    private Provincia provincia;
}

