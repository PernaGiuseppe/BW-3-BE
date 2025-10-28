package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.entities.Cliente;
import buildweek5.BW_3_BE.payloads.ClienteDTO;
import buildweek5.BW_3_BE.payloads.ClienteFilterDTO;
import buildweek5.BW_3_BE.services.ClienteService;
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
@RequestMapping("/api/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Cliente> createCliente(@RequestBody @Valid ClienteDTO dto) {
        Cliente nuovoCliente = clienteService.createCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoCliente);
    }

    @GetMapping
    public ResponseEntity<Page<Cliente>> getAllClienti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Cliente> clienti = clienteService.findAllClienti(pageable);
        return ResponseEntity.ok(clienti);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Cliente>> filterClienti(
            @RequestParam(required = false) BigDecimal fatturatoMin,
            @RequestParam(required = false) BigDecimal fatturatoMax,
            @RequestParam(required = false) LocalDate dataInserimentoInizio,
            @RequestParam(required = false) LocalDate dataInserimentoFine,
            @RequestParam(required = false) LocalDate dataUltimoContattoInizio,
            @RequestParam(required = false) LocalDate dataUltimoContattoFine,
            @RequestParam(required = false) String nomeContiene,
            @RequestParam(required = false) String provinciaSedeLegale,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {

        ClienteFilterDTO filters = new ClienteFilterDTO();
        filters.setFatturatoMin(fatturatoMin);
        filters.setFatturatoMax(fatturatoMax);
        filters.setDataInserimentoInizio(dataInserimentoInizio);
        filters.setDataInserimentoFine(dataInserimentoFine);
        filters.setDataUltimoContattoInizio(dataUltimoContattoInizio);
        filters.setDataUltimoContattoFine(dataUltimoContattoFine);
        filters.setNomeContiene(nomeContiene);
        filters.setProvinciaSedeLegale(provinciaSedeLegale);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Cliente> clienti = clienteService.filterClienti(filters, pageable);
        return ResponseEntity.ok(clienti);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Cliente> updateCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteDTO dto) {
        Cliente clienteAggiornato = clienteService.updateCliente(id, dto);
        return ResponseEntity.ok(clienteAggiornato);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}
