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
public class AlbumInStudioDTO {

    private String titoloAlbumInStudio;

    private Date dataRilascio;

    private List<String> brani;

    private List <String>  generi;

    private Integer idArtistaMusicale;
}
