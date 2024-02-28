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
@Table(name = "`release`")
public class Release {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRelease")
    private Integer idRelease;

    @Column(name = "idReleaseGroupMusicBrainz")
    private String idReleaseGroupMusicBrainz;

    @Column(name = "idReleaseMusicBrainz")
    private String idReleaseMusicBrainz;

    @Column(name = "title")
    private String title;

    @Column(name = "kind")
    private String kind;

   @Column(name = "coverArt")
   private String coverArt;

    @Column(name = "dateOfRelease")
    private String dateOfRelease;

    @ElementCollection
    private List<String> tracks;

    @ElementCollection
    private List <String> genres;

    @ManyToMany(mappedBy = "releases")
    private List<Artist> artists = new ArrayList<>();

}
