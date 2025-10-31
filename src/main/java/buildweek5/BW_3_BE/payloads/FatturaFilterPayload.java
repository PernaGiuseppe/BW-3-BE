package buildweek5.BW_3_BE.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
public class FatturaFilterPayload{
    private Long clienteId;
    private Long statoFatturaId;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Integer anno;
    private BigDecimal importoMin;
    private BigDecimal importoMax;
}


