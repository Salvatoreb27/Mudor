package Mudor.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrossArtistaSingoloDTO {

    private String titoloSingolo;

    private String nomeArtista;

}
