package buildweek5.BW_3_BE.services;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvinciaRepresentation {
    @CsvBindByName(column = "Sigla")
    private String sigla;
    @CsvBindByName(column = "Provincia")
    private String provincia;
    @CsvBindByName(column = "Regione")
    private String regione;
}
