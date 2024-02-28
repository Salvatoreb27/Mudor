package Mudor.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseDTO {

    private String idReleaseGroupMusicBrainz;

    private String idReleaseMusicBrainz;

    private String title;

    private String kind;

    private String coverArt;

    private String dateOfRelease;

    private List<String> tracks;

    private List <String> genres;

    private List<ArtistDTO> artistDTOList;
}
