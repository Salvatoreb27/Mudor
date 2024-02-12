package Mudor.DTO;

import jakarta.persistence.*;
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
public class ArtistaMusicaleDTO {

    private String nome;

    private String descrizione;

    private List<String> generi;

    private String paeseDOrigine;

    private List<AlbumInStudioDTO> albumInStudioDTOS = new ArrayList<>();

    private List<AlbumLiveDTO> albumLiveDTOS = new ArrayList<>();

    private List<RaccoltaDTO> raccoltaDTOS = new ArrayList<>();

    private List<CrossArtistaSingoloDTO> crossArtistaSingoloDTOS = new ArrayList<>();
}
