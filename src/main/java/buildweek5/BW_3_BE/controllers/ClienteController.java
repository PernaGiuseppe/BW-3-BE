package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Provincia;
import buildweek5.BW_3_BE.exceptions.NotValidException;
import buildweek5.BW_3_BE.payloads.ClienteDTO;
import buildweek5.BW_3_BE.payloads.ClienteFilterPayload;
import buildweek5.BW_3_BE.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @GetMapping
    public Page<Cliente> getAllClienti(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "ragioneSociale") String sortBy){
        return clienteService.findAllClienti(page, size, sortBy);
    }
    @GetMapping("{id}")
    public Cliente getCliente(@PathVariable Long id){
        return clienteService.findById(id);
    }

    @GetMapping("/filter")
    public Page<Cliente> filterClienti(@RequestParam(required = false) BigDecimal fatturatoMin,
                                       @RequestParam(required = false) BigDecimal fatturatoMax,
                                       @RequestParam(required = false) LocalDate dataInserimentoInizio,
                                       @RequestParam(required = false) LocalDate dataInserimentoFine,
                                       @RequestParam(required = false) LocalDate dataUltimoContattoInizio,
                                       @RequestParam(required = false) LocalDate dataUltimoContattoFine,
                                       @RequestParam(required = false) String nomeContiene,
                                       @RequestParam(required = false) Provincia provinciaSedeLegale,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "ragioneSociale") String sortBy){

        ClienteFilterPayload filters = new ClienteFilterPayload();
        filters.setFatturatoMin(fatturatoMin);
        filters.setFatturatoMax(fatturatoMax);
        filters.setDataInserimentoInizio(dataInserimentoInizio);
        filters.setDataInserimentoFine(dataInserimentoFine);
        filters.setDataUltimoContattoInizio(dataUltimoContattoInizio);
        filters.setDataUltimoContattoFine(dataUltimoContattoFine);
        filters.setContieneNome(nomeContiene);
        filters.setProvincia(provinciaSedeLegale);
        return clienteService.filterClienti(filters, page, size, sortBy);
    }
}
