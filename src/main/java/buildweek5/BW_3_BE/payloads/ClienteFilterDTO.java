package buildweek5.BW_3_BE.payloads;

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
public class ClienteFilterDTO {
    private BigDecimal fatturatoMin;
    private BigDecimal fatturatoMax;
    private LocalDate dataInserimentoInizio;
    private LocalDate dataInserimentoFine;
    private LocalDate dataUltimoContattoInizio;
    private LocalDate dataUltimoContattoFine;
    private String nomeContiene;
    private String provinciaSedeLegale;
}

