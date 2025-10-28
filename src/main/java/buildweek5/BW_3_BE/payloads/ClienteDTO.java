package buildweek5.BW_3_BE.payloads;

import buildweek5.BW_3_BE.entities.TipoCliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    @NotBlank(message = "La ragione sociale è obbligatoria")
    private String ragioneSociale;

    @NotBlank(message = "La partita IVA è obbligatoria")
    @Size(min = 11, max = 11, message = "La partita IVA deve essere di 11 caratteri")
    private String partitaIva;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    private BigDecimal fatturatoAnnuale;

    @Email(message = "Formato PEC non valido")
    private String pec;

    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;

    @NotNull(message = "Il tipo cliente è obbligatorio")
    private TipoCliente tipoCliente;

    @NotNull(message = "L'indirizzo legale è obbligatorio")
    @Valid
    private IndirizzoDTO indirizzoLegale;

    @NotNull(message = "L'indirizzo operativo è obbligatorio")
    @Valid
    private IndirizzoDTO indirizzoOperativo;
}
