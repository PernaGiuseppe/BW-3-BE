package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.entities.Utente;
import buildweek5.BW_3_BE.payloads.UpdateRuoloDTO;
import buildweek5.BW_3_BE.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente updateUserRole(@PathVariable Long id, @RequestBody UpdateRuoloDTO body) {
        return utenteService.udpdateRuolo(id, body.ruolo());
    }
}