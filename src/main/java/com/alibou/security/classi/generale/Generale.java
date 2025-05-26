package com.alibou.security.classi.generale;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "generale")
public class Generale {
    @Id
    private Integer id;
    private String nome;
    private String impianto;
    private String versione;
    private Integer pollingtime;
}
