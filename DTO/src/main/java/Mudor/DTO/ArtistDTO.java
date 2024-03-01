package Mudor.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * ArtistDTO è una classe che rappresenta un artista musicale.
 * Contiene informazioni sull'artista, inclusi identificatori, nome, una lista di URL di relazione,
 * descrizione, generi musicali, paese di origine e una lista di release associate.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {

    /**
     * Identificatore univoco dell'artista su MusicBrainz.
     */
    private String idArtistMusicBrainz;

    /**
     * Nome dell'artista.
     */
    private String name;

    /**
     * Lista degli URL di relazione associati all'artista.
     */
    private List<String> relationURLs;

    /**
     * Descrizione dell'artista.
     */
    private String description;

    /**
     * Lista dei generi musicali dell'artista.
     */
    private List<String> genres;

    /**
     * Paese di origine dell'artista.
     */
    private String country;

    /**
     * Lista delle release associate all'artista.
     */
    private List<ReleaseDTO> releaseDTOList = new ArrayList<>();

}
