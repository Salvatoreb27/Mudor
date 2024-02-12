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

    private List<AlbumInStudioDTO> albumsInStudioDTOS = new ArrayList<>();

    private List<AlbumLiveDTO> albumsLiveDTOS = new ArrayList<>();

    private List<RaccoltaDTO> raccoltaDTOS = new ArrayList<>();

    private List<String> singoli = new ArrayList<>();
}
