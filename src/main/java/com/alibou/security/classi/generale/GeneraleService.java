package com.alibou.security.classi.generale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneraleService {
    @Autowired
    private GeneraleRepository generaleRepository;

    public Generale getGenerale() {
        List<Generale> generaleList = generaleRepository.findAll();

        if (!generaleList.isEmpty()) {
            return generaleList.get(0);
        } else {
            throw new IllegalStateException("Generale non trovato");
        }
    }
}
