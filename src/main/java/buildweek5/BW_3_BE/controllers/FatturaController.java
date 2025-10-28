package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.entities.Fattura;
import buildweek5.BW_3_BE.payloads.FatturaDTO;
import buildweek5.BW_3_BE.payloads.FatturaFilterDTO;
import buildweek5.BW_3_BE.services.FatturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Fattura> createFattura(@RequestBody @Valid FatturaDTO dto) {
        Fattura nuovaFattura = fatturaService.createFattura(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovaFattura);
    }

    @GetMapping
    public ResponseEntity<Page<Fattura>> getAllFatture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Fattura> fatture = fatturaService.findAll(pageable);
        return ResponseEntity.ok(fatture);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fattura> getFatturaById(@PathVariable Long id) {
        Fattura fattura = fatturaService.findById(id);
        return ResponseEntity.ok(fattura);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Fattura>> filterFatture(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long statoFatturaId,
            @RequestParam(required = false) LocalDate dataInizio,
            @RequestParam(required = false) LocalDate dataFine,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) BigDecimal importoMin,
            @RequestParam(required = false) BigDecimal importoMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String sortBy) {

        FatturaFilterDTO filters = new FatturaFilterDTO();
        filters.setClienteId(clienteId);
        filters.setStatoFatturaId(statoFatturaId);
        filters.setDataInizio(dataInizio);
        filters.setDataFine(dataFine);
        filters.setAnno(anno);
        filters.setImportoMin(importoMin);
        filters.setImportoMax(importoMax);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Fattura> fatture = fatturaService.filterFatture(filters, pageable);
        return ResponseEntity.ok(fatture);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Fattura> updateFattura(
            @PathVariable Long id,
            @RequestBody @Valid FatturaDTO dto) {
        Fattura fatturaAggiornata = fatturaService.updateFattura(id, dto);
        return ResponseEntity.ok(fatturaAggiornata);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteFattura(@PathVariable Long id) {
        fatturaService.deleteFattura(id);
        return ResponseEntity.noContent().build();
    }
}

