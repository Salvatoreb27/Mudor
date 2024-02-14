package Mudor.servicesimpl;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.ArtistaMusicaleDTO;
import Mudor.entity.*;
import Mudor.entity.spec.AlbumInStudioSpecifications;
import Mudor.repository.AlbumInStudioRepository;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.services.AlbumInStudioService;
import Mudor.services.ArtistaMusicaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;


@Service
public class AlbumInStudioServiceImpl implements AlbumInStudioService {
    @Autowired
    private AlbumInStudioRepository albumInStudioRepository;
    @Autowired
    private ArtistaMusicaleRepository artistaMusicaleRepository;


    @Override
    public AlbumInStudioDTO mapTODTO(AlbumInStudio albumInStudio) {
        AlbumInStudioDTO albumInStudioDTO = AlbumInStudioDTO.builder()
                .titoloAlbumInStudio(albumInStudio.getTitoloAlbumInStudio())
                .dataRilascio(albumInStudio.getDataRilascio())
                .brani(albumInStudio.getBrani())
                .generi(albumInStudio.getGeneri())
                .idArtistaMusicale(albumInStudio.getArtistaMusicale().getIdArtista())
                .build();
        return albumInStudioDTO;
    }

    @Override
    public List<AlbumInStudioDTO> mapTODTOList(List<AlbumInStudio> albumInStudioList) {
        return albumInStudioList.stream()
                .map((AlbumInStudio albumInStudio) -> this.mapTODTO(albumInStudio))
                .collect(Collectors.toList());
    }

    @Override
    public AlbumInStudio mapToEntity(AlbumInStudioDTO albumInStudioDTO) {
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(albumInStudioDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        AlbumInStudio albumInStudio = AlbumInStudio.builder()
                .titoloAlbumInStudio(albumInStudioDTO.getTitoloAlbumInStudio())
                .dataRilascio(albumInStudioDTO.getDataRilascio())
                .brani(albumInStudioDTO.getBrani())
                .generi(albumInStudioDTO.getGeneri())
                .artistaMusicale(artistaMusicale)
                .build();
        return null;
    }

    @Override
    public List<AlbumInStudio> mapTOEntityList(List<AlbumInStudioDTO> albumInStudioDTOList) {
        return albumInStudioDTOList.stream()
                .map((AlbumInStudioDTO albumInStudioDTO) -> this.mapToEntity(albumInStudioDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumInStudioDTO> getDTOs() {
        List<AlbumInStudio> albumInStudioList = new ArrayList<>(albumInStudioRepository.findAll());
        return mapTODTOList(albumInStudioList);
    }

    @Override
    public AlbumInStudioDTO getDTO(Integer id) {
        Optional<AlbumInStudio> albumInStudio = albumInStudioRepository.findById(id);
        if (albumInStudio.isPresent()) {
            return mapTODTO(albumInStudio.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(AlbumInStudioDTO albumInStudioDTO) {
        AlbumInStudio albumInStudio = mapToEntity(albumInStudioDTO);
        albumInStudioRepository.save(albumInStudio);
    }

    @Override
    public void update(AlbumInStudioDTO albumInStudioDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(albumInStudioDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        AlbumInStudio albumInStudioRicercato = albumInStudioRepository.findById(id).orElseThrow(() -> new RuntimeException("L'album in studio ricercato non è presente"));
        albumInStudioRicercato.setTitoloAlbumInStudio(albumInStudioDTO.getTitoloAlbumInStudio() == null ? albumInStudioRicercato.getTitoloAlbumInStudio() : albumInStudioDTO.getTitoloAlbumInStudio());
        albumInStudioRicercato.setDataRilascio(albumInStudioDTO.getDataRilascio() == null ? albumInStudioRicercato.getDataRilascio() : albumInStudioDTO.getDataRilascio());
        albumInStudioRicercato.setGeneri(albumInStudioDTO.getGeneri() == null ? albumInStudioRicercato.getGeneri() : albumInStudioDTO.getGeneri());
        albumInStudioRicercato.setBrani(albumInStudioDTO.getBrani() == null ? albumInStudioRicercato.getBrani() : albumInStudioDTO.getBrani());
        albumInStudioRicercato.setArtistaMusicale(artistaMusicale == null ? albumInStudioRicercato.getArtistaMusicale() : artistaMusicale);
        albumInStudioRepository.save(albumInStudioRicercato);
    }

    @Override
    public void delete(Integer id) {
        if (albumInStudioRepository.existsById(id)) {
            albumInStudioRepository.deleteById(id);
        }
    }

    private List<Specification<AlbumInStudio>> createSpecifications(Integer idAlbumInStudio, String titoloAlbumInStudio, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<AlbumInStudio>> specifications = new ArrayList<>();
        if (idAlbumInStudio != null) {
            specifications.add(AlbumInStudioSpecifications.likeIdAlbumInStudio(idAlbumInStudio));
        }
        if (titoloAlbumInStudio != null) {
            specifications.add(AlbumInStudioSpecifications.likeTitoloAlbumInStudio(titoloAlbumInStudio));
        }
        if (dataRilascio != null) {
            specifications.add(AlbumInStudioSpecifications.likeDataRilascio(dataRilascio));
        }
        if (brani != null) {
            specifications.add(AlbumInStudioSpecifications.likeBrani(brani));
        }
        if (generi != null) {
            specifications.add(AlbumInStudioSpecifications.likeGeneri(generi));
        }
        if (nome != null) {
            specifications.add(AlbumInStudioSpecifications.likeArtistaAlbum(nome));
        }
        return specifications;
    }
    private Specification<AlbumInStudio> combineSpecifications(List<Specification<AlbumInStudio>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }

    @Override
    public List<AlbumInStudioDTO> getAlbumInStudioBy(Integer idAlbumInStudio, String titoloAlbumInStudio, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<AlbumInStudio>> specifications = createSpecifications(idAlbumInStudio, titoloAlbumInStudio, dataRilascio, brani, generi, nome);
        Specification<AlbumInStudio> combinedSpecification = combineSpecifications(specifications);

        List<AlbumInStudio> listaAlbumInStudio = albumInStudioRepository.findAll((Sort) combinedSpecification);

        return listaAlbumInStudio.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }
}
