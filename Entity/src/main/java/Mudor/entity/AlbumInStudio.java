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
@Table(name = "albumInStudio")
public class AlbumInStudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlbumInStudio")
    private Integer idAlbumInStudio;

    @Column(name = "titoloAlbumInStudio")
    private String titoloAlbumInStudio;

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
