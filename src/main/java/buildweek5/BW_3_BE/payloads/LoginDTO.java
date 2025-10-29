package buildweek5.BW_3_BE.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record LoginDTO(
        @Email(message = "Indirizzo e-mail inserito nel formato sbagliato")
        String email,
        @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$", message = "La password deve contenere da 8 a 16 caratteri, un numero, almeno una minuscola e una maiuscola")
        String password
) {
}
