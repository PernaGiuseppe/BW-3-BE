package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.exceptions.NotValidException;
import buildweek5.BW_3_BE.payloads.ClienteDTO;
import buildweek5.BW_3_BE.payloads.FatturaDT0;
import buildweek5.BW_3_BE.payloads.FatturaFilterPayload;
import buildweek5.BW_3_BE.services.FattureService;
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
@RequestMapping("/fatture")
public class FattureController {
    @Autowired
    private FattureService fattureService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Fattura createFattura(@RequestBody @Validated FatturaDT0 body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorMessages = validationResult.getFieldErrors().stream().
                    map(fieldError -> fieldError.getField() + " :" + fieldError.getDefaultMessage()).toList();
            throw new NotValidException(errorMessages);
        }
        return fattureService.createFattura(body);
    }
    @GetMapping
    public Page<Fattura> getAllFatture(@RequestParam(required = false) Long clienteId,
                                       @RequestParam(required = false) Long statoFatturaId,
                                       @RequestParam(required = false) LocalDate dataInizio,
                                       @RequestParam(required = false) LocalDate dataFine,
                                       @RequestParam(required = false) Integer anno,
                                       @RequestParam(required = false) BigDecimal importoMin,
                                       @RequestParam(required = false) BigDecimal importoMax,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "numero") String sortBy){
        FatturaFilterPayload filters = new FatturaFilterPayload();
        filters.setClienteId(clienteId);
        filters.setStatoFatturaId(statoFatturaId);
        filters.setDataInizio(dataInizio);
        filters.setDataFine(dataFine);
        filters.setAnno(anno);
        filters.setImportoMin(importoMin);
        filters.setImportoMax(importoMax);
        return fattureService.findAllFiltered(filters, page, size, sortBy);
    }
    @GetMapping("{id}")
    public Fattura findById(@PathVariable Long id){
        return fattureService.findById(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura updateFattura(@PathVariable Long id, @RequestBody @Validated FatturaDT0 body, BindingResult validationResults){
        if(validationResults.hasErrors()){
            List<String> errorMessages = validationResults.getFieldErrors().stream().
                    map(fieldError -> fieldError.getField() + " :" + fieldError.getDefaultMessage()).toList();
            throw new NotValidException(errorMessages);
        }
        return fattureService.updateFattura(id, body);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        fattureService.deleteFattura(id);
    }
}
