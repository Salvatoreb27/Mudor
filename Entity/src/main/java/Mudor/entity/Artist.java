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
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArtist")
    private Integer idArtist;

    @Column(name = "idArtistMusicBrainz")
    private String idArtistMusicBrainz;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ElementCollection
    private List<String> genres;

    @Column(name = "country")
    private String country;

    @ManyToMany
    @JoinTable(
            name = "artist_release",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "release_id")
    )
    private List<Release> releases = new ArrayList<>();



}
