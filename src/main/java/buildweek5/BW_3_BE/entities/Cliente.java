package buildweek5.BW_3_BE.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String ragioneSociale;

    @Column(nullable = false, unique = true, length = 11)
    private String partitaIva;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate dataInserimento;

    private LocalDate dataUltimoContatto;

    @Column(precision = 15, scale = 2)
    private BigDecimal fatturatoAnnuale;

    @Column(unique = true)
    private String pec;

    private String telefono;

    private String emailContatto;

    private String nomeContatto;

    private String cognomeContatto;

    private String telefonoContatto;

    private String logoAziendale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCliente tipoCliente;

    @OneToOne
    @JoinColumn(name = "indirizzo_legale_id", nullable = false)
    private Indirizzo indirizzoLegale;

    @OneToOne
    @JoinColumn(name = "indirizzo_operativo_id", nullable = false)
    private Indirizzo indirizzoOperativo;

}
