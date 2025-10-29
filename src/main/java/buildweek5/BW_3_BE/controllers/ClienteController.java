package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.exceptions.NotValidException;
import buildweek5.BW_3_BE.payloads.ClienteDTO;
import buildweek5.BW_3_BE.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente createCliente(@RequestBody @Validated ClienteDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorMessages = validationResult.getFieldErrors().stream().
                    map(fieldError -> fieldError.getField() + " :" + fieldError.getDefaultMessage()).toList();
            throw new NotValidException(errorMessages);
        }
        return clienteService.createCliente(body);
    }
}
