package Mudor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "albumLive")
public class AlbumLive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlbumLive")
    private Integer idAlbumLive;

    @Column(name = "titoloAlbumLive")
    private String titoloAlbumLive;

    @Column(name = "dataRilascio")
    private Date dataRilascio;

    @ElementCollection
    private List<String> brani;

    @ElementCollection
    private List <String>  generi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idArtistaMusicale")
    private ArtistaMusicale artistaMusicale;
}
