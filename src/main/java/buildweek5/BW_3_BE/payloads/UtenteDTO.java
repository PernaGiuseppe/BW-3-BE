package buildweek5.BW_3_BE.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotBlank(message = "L'username è obbligatorio")
        @Size(min = 2, max = 20, message = "Username deve avere una grandezza compresa tra 2 e 20 caratteri")
        String username,
        @NotBlank(message = "L'indirizzo e-mail non può essere vuoto")
        @Email(message = "Indirizzo e-mail inserito nel formato sbagliato")
        String email,
        @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$", message = "La password deve contenere da 8 a 16 caratteri, un numero, almeno una minuscola e una maiuscola")
        String password,
        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il nome deve avere una grandezza compresa tra 2 e 20 caratteri")
        String nome,
        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il cognome deve avere una grandezza compresa tra 2 e 20 caratteri")
        String cognome
) {
}
