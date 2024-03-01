package Mudor.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ReleaseDTO è una classe che rappresenta una release musicale.
 * Contiene informazioni sulla release, inclusi identificatori, titolo, tipo,
 * copertina, data di rilascio, elenco delle tracce, generi musicali e lista degli artisti associati.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDTO {

    /**
     * Identificatore univoco del gruppo di release su MusicBrainz.
     * (per gruppo di Release si intende la release inquadrata per titolo)
     */
    private String idReleaseGroupMusicBrainz;

    /**
     * Identificatore univoco della versione della release su MusicBrainz.
     * (per versione della Release si intende ad esempio: Limited Edition, Platinum Edition, Remaster, ecc.)
     */
    private String idReleaseMusicBrainz;

    /**
     * Titolo della release.
     */
    private String title;

    /**
     * Tipo di release (es. Album, Singolo, EP, etc.).
     */
    private String kind;

    /**
     * URL della copertina della release.
     */
    private String coverArt;

    /**
     * Data di rilascio della release.
     */
    private String dateOfRelease;

    /**
     * Lista delle tracce della release.
     */
    private List<String> tracks;

    /**
     * Lista dei generi musicali della release.
     */
    private List<String> genres;

    /**
     * Lista degli artisti associati alla release.
     */
    private List<ArtistDTO> artistDTOList;
}
