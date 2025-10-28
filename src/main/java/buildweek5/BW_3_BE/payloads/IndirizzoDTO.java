package buildweek5.BW_3_BE.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndirizzoDTO {

    @NotBlank(message = "La via è obbligatoria")
    private String via;

    @NotBlank(message = "Il numero civico è obbligatorio")
    private String civico;

    private String localita;

    @NotBlank(message = "Il CAP è obbligatorio")
    @Size(min = 5, max = 5, message = "Il CAP deve essere di 5 caratteri")
    private String cap;

    @NotNull(message = "Il comune è obbligatorio")
    private Long comuneId;
}
