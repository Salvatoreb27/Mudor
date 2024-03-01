package Mudor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe Release rappresenta un'entità release nel sistema.
 * Contiene informazioni su una release musicale, tra cui il suo identificatore,
 * l'ID del gruppo di release su MusicBrainz, l'ID della release su MusicBrainz,
 * il titolo, il tipo, l'URL della copertina, la data di rilascio, le tracce,
 * i generi musicali e gli artisti associati.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`release`")
public class Release {

    /**
     * Identificatore univoco della release nel sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRelease")
    private Integer idRelease;

    /**
     * Identificatore del gruppo di release su MusicBrainz.
     */
    @Column(name = "idReleaseGroupMusicBrainz")
    private String idReleaseGroupMusicBrainz;

    /**
     * Identificatore della release su MusicBrainz.
     */
    @Column(name = "idReleaseMusicBrainz")
    private String idReleaseMusicBrainz;

    /**
     * Titolo della release.
     */
    @Column(name = "title")
    private String title;

    /**
     * Tipo di release.
     */
    @Column(name = "kind")
    private String kind;

    /**
     * URL della copertina della release.
     */
    @Column(name = "coverArt")
    private String coverArt;

    /**
     * Data di rilascio della release.
     */
    @Column(name = "dateOfRelease")
    private String dateOfRelease;

    /**
     * Lista delle tracce della release.
     */
    @ElementCollection
    private List<String> tracks;

    /**
     * Lista dei generi musicali della release.
     */
    @ElementCollection
    private List<String> genres;

    /**
     * Lista degli artisti associati alla release.
     */
    @ManyToMany(mappedBy = "releases")
    private List<Artist> artists = new ArrayList<>();
}