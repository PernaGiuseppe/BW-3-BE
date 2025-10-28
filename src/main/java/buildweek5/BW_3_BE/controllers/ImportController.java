package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.services.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    @Autowired
    private CSVImportService importService;

    @PostMapping("/province")
    public ResponseEntity<String> importProvince() {
        try {
            importService.importProvince("province-italiane.csv");
            return ResponseEntity.ok("Province importate con successo");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/comuni")
    public ResponseEntity<String> importComuni() {
        try {
            importService.importComuni("comuni-italiani.csv");
            return ResponseEntity.ok("Comuni importati con successo");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore: " + e.getMessage());
        }
    }
}
