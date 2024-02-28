package Mudor.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDTO {

    private String idArtistMusicBrainz;

    private String name;

    private String description;

    private List<String> genres;

    private String country;

    private List<ReleaseDTO> releaseDTOList = new ArrayList<>();

}
