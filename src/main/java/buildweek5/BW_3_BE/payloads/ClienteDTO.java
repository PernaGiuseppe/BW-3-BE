package buildweek5.BW_3_BE.payloads;

import buildweek5.BW_3_BE.entities.Indirizzo;
import buildweek5.BW_3_BE.entities.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ClienteDTO(
        @NotBlank(message = "Ragione sociale obbligatoria")
        String ragioneSociale,
        @NotBlank(message = "La partita IVA è obbligatoria")
        @Size(min = 11, max = 11, message = "La partita IVA deve essere di 11 caratteri")
        String partitaIva,
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "Formato email non valido")
        String email,
        BigDecimal fatturatoAnnuale,
        @Email(message = "Formato PEC non valido")
        String pec,
        String telefono,
        @Email(message = "Formato email non valido")
        String emailContatto,
        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il nome deve avere una grandezza compresa tra 2 e 20 caratteri")
        String nomeContatto,
        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il cognome deve avere una grandezza compresa tra 2 e 20 caratteri")
        String cognomeContatto,
        String telefonoContatto,
        @NotNull(message = "Il tipo cliente è obbligatorio")
        TipoCliente tipoCliente,
        @NotNull(message = "L'indirizzo legale è obbligatorio")
        IndirizzoDTO indirizzoLegale,
        @NotNull(message = "L'indirizzo operativo è obbligatorio")
        IndirizzoDTO indirizzoOperativo

) {
}
