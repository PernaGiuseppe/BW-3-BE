package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.entities.Ruolo;
import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.exceptions.BadRequestException;
import buildweek5.BW_3_BE.exceptions.NotValidException;
import buildweek5.BW_3_BE.payloads.LoginDTO;
import buildweek5.BW_3_BE.payloads.LoginResponseDTO;
import buildweek5.BW_3_BE.payloads.UtenteDTO;
import buildweek5.BW_3_BE.services.AuthService;
import buildweek5.BW_3_BE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utentiService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated LoginDTO payload, BindingResult validationResult) {
        if(validationResult.hasErrors()){
            List<String> errorMessages = validationResult.getFieldErrors().stream().
                    map(fieldError -> fieldError.getField() + " :" + fieldError.getDefaultMessage()).toList();throw new NotValidException(errorMessages);
        }
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(payload));
    }


}

