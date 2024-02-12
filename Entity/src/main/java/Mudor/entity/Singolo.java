package Mudor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Singolo")
public class Singolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSingolo")
    private Integer idSingolo;

    @Column(name = "titoloSingolo")
    private String titoloSingolo;

    @Column(name = "dataRilascio")
    private Date dataRilascio;

    @ElementCollection
    private List <String>  generi;

    @OneToMany(mappedBy = "singolo",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CrossArtistaSingolo> crossArtistaSingolos = new ArrayList<>();
}
