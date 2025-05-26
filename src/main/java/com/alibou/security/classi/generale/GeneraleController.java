package com.alibou.security.classi.generale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generale")
@CrossOrigin
public class GeneraleController {
    @Autowired
    private GeneraleService generaleService;

    @GetMapping
    public ResponseEntity<Generale> getGenerale() {
        Generale generale = generaleService.getGenerale();
        return ResponseEntity.ok(generale);
    }
}
