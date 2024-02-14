package Mudor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artistaMusicale")
public class ArtistaMusicale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArtistaMusicale")
    private Integer idArtista;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @ElementCollection
    private List<String> generi;

    @Column(name = "paeseDOrigine")
    private String paeseDOrigine;

    @OneToMany(mappedBy = "artistaMusicale",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlbumInStudio> albumsInStudio = new ArrayList<>();

    @OneToMany(mappedBy = "artistaMusicale",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlbumLive> albumsLive = new ArrayList<>();

    @OneToMany(mappedBy = "artistaMusicale",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Raccolta> raccolte = new ArrayList<>();

    @OneToMany(mappedBy = "artistaMusicale",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CrossArtistaSingolo> crossArtistaSingolos = new ArrayList<>();


}
