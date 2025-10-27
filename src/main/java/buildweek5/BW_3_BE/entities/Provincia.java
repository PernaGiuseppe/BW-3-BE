package buildweek5.BW_3_BE.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Provincia {

    @Id
    @Column(nullable = false, unique = true, length = 10)
    private String sigla;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String regione;
}
