package buildweek5.BW_3_BE.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//(exclude = {"comune"})
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String via;

    @Column(nullable = false)
    private String civico;

    private String localita;

    @Column(nullable = false, length = 5)
    private String cap;

    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;


}
