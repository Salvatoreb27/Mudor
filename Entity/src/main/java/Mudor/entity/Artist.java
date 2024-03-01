package Mudor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Artist rappresenta un'entità artista nel sistema.
 * Contiene informazioni su un artista musicale, tra cui il suo identificatore,
 * il nome, gli URL delle relazioni, la descrizione, i generi musicali, il paese di origine e le release associate.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artist")
public class Artist {

    /**
     * Identificatore univoco dell'artista nel sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArtist")
    private Integer idArtist;

    /**
     * Identificatore dell'artista su MusicBrainz.
     */
    @Column(name = "idArtistMusicBrainz")
    private String idArtistMusicBrainz;

    /**
     * Nome dell'artista.
     */
    @Column(name = "name")
    private String name;

    /**
     * Lista degli URL delle relazioni dell'artista.
     */
    @ElementCollection
    private List<String> relationURLs;

    /**
     * Descrizione dell'artista.
     */
    @Column(name = "description")
    private String description;

    /**
     * Lista dei generi musicali associati all'artista.
     */
    @ElementCollection
    private List<String> genres;

    /**
     * Paese di origine dell'artista.
     */
    @Column(name = "country")
    private String country;

    /**
     * Lista delle release associate all'artista.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "artist_release",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "release_id")
    )
    private List<Release> releases = new ArrayList<>();
}