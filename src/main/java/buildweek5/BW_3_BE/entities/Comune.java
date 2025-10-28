package buildweek5.BW_3_BE.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comuni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "provincia")
public class Comune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "codice_provincia", nullable = false, length = 3)
    private String codiceProvincia;

    @Column(name = "progressivo_comune", nullable = false, length = 3)
    private String progressivoComune;

    @Column(name = "denominazione", nullable = false)
    private String denominazione;

    @ManyToOne
    @JoinColumn(name = "provincia_sigla", nullable = false)
    private Provincia provincia;
}
