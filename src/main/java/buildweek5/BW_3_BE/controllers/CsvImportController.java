package buildweek5.BW_3_BE.controllers;

import buildweek5.BW_3_BE.services.ComuniService;
import buildweek5.BW_3_BE.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/import")
public class CsvImportController {

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private ComuniService comuniService;

    @PostMapping(value = "/province", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> importProvince(@RequestParam("file") MultipartFile file) throws IOException {
       return ResponseEntity.status(HttpStatus.CREATED).body(provinceService.upload(file));
    }

    @PostMapping(value = "/comuni", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> importComuni(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(comuniService.upload(file));
    }

}
