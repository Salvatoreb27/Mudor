package Mudor.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crossArtistaSingolo")
public class CrossArtistaSingolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCrossArtistaSingolo")
    private Integer idCrossArtistaSingolo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idArtistaMusicale")
    private ArtistaMusicale artistaMusicale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSingolo")
    private Singolo singolo;

}
