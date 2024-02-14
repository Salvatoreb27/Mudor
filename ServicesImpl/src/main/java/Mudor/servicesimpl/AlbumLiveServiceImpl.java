package Mudor.servicesimpl;

import Mudor.DTO.AlbumInStudioDTO;
import Mudor.DTO.AlbumLiveDTO;
import Mudor.entity.AlbumInStudio;
import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import Mudor.entity.spec.AlbumInStudioSpecifications;
import Mudor.entity.spec.AlbumLiveSpecifications;
import Mudor.repository.AlbumLiveRepository;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.services.AlbumLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumLiveServiceImpl implements AlbumLiveService {

    @Autowired
    private AlbumLiveRepository albumLiveRepository;

    @Autowired
    private ArtistaMusicaleRepository artistaMusicaleRepository;

    @Override
    public AlbumLiveDTO mapTODTO(AlbumLive albumLive) {
        AlbumLiveDTO albumLiveDTO = AlbumLiveDTO.builder()
                .titoloAlbumLive(albumLive.getTitoloAlbumLive())
                .dataRilascio(albumLive.getDataRilascio())
                .brani(albumLive.getBrani())
                .generi(albumLive.getGeneri())
                .idArtistaMusicale(albumLive.getArtistaMusicale().getIdArtista())
                .build();
        return albumLiveDTO;
    }

    @Override
    public List<AlbumLiveDTO> mapTODTOList(List<AlbumLive> albumLiveList) {
        return albumLiveList.stream()
                .map((AlbumLive albumLive) -> this.mapTODTO(albumLive))
                .collect(Collectors.toList());
    }

    @Override
    public AlbumLive mapToEntity(AlbumLiveDTO albumLiveDTO) {
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(albumLiveDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        AlbumLive albumLive = AlbumLive.builder()
                .titoloAlbumLive(albumLiveDTO.getTitoloAlbumLive())
                .dataRilascio(albumLiveDTO.getDataRilascio())
                .brani(albumLiveDTO.getBrani())
                .generi(albumLiveDTO.getGeneri())
                .artistaMusicale(artistaMusicale)
                .build();
        return null;
    }

    @Override
    public List<AlbumLive> mapTOEntityList(List<AlbumLiveDTO> albumLiveDTOList) {
        return albumLiveDTOList.stream()
                .map((AlbumLiveDTO albumLiveDTO) -> this.mapToEntity(albumLiveDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumLiveDTO> getDTOs() {
        List<AlbumLive> albumLiveList = new ArrayList<>(albumLiveRepository.findAll());
        return mapTODTOList(albumLiveList);
    }

    @Override
    public AlbumLiveDTO getDTO(Integer id) {
        Optional<AlbumLive> albumLive = albumLiveRepository.findById(id);
        if (albumLive.isPresent()) {
            return mapTODTO(albumLive.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(AlbumLiveDTO albumLiveDTO) {
        AlbumLive albumLive = mapToEntity(albumLiveDTO);
        albumLiveRepository.save(albumLive);
    }

    @Override
    public void update(AlbumLiveDTO albumLiveDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(albumLiveDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        AlbumLive albumLiveRicercato = albumLiveRepository.findById(id).orElseThrow(() -> new RuntimeException("L'album live ricercato non è presente"));
        albumLiveRicercato.setTitoloAlbumLive(albumLiveDTO.getTitoloAlbumLive() == null ? albumLiveRicercato.getTitoloAlbumLive() : albumLiveDTO.getTitoloAlbumLive());
        albumLiveRicercato.setDataRilascio(albumLiveDTO.getDataRilascio() == null ? albumLiveRicercato.getDataRilascio() : albumLiveDTO.getDataRilascio());
        albumLiveRicercato.setGeneri(albumLiveDTO.getGeneri() == null ? albumLiveRicercato.getGeneri() : albumLiveDTO.getGeneri());
        albumLiveRicercato.setBrani(albumLiveDTO.getBrani() == null ? albumLiveRicercato.getBrani() : albumLiveDTO.getBrani());
        albumLiveRicercato.setArtistaMusicale(artistaMusicale == null ? albumLiveRicercato.getArtistaMusicale() : artistaMusicale);
        albumLiveRepository.save(albumLiveRicercato);
    }

    @Override
    public void delete(Integer id) {
        if (albumLiveRepository.existsById(id)) {
            albumLiveRepository.deleteById(id);
        }
    }
    private List<Specification<AlbumLive>> createSpecifications(Integer idAlbumLive, String titoloAlbumLive, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<AlbumLive>> specifications = new ArrayList<>();
        if (idAlbumLive != null) {
            specifications.add(AlbumLiveSpecifications.likeIdAlbumLive(idAlbumLive));
        }
        if (titoloAlbumLive != null) {
            specifications.add(AlbumLiveSpecifications.likeTitoloAlbumLive(titoloAlbumLive));
        }
        if (dataRilascio != null) {
            specifications.add(AlbumLiveSpecifications.likeDataRilascio(dataRilascio));
        }
        if (brani != null) {
            specifications.add(AlbumLiveSpecifications.likeBrani(brani));
        }
        if (generi != null) {
            specifications.add(AlbumLiveSpecifications.likeGeneri(generi));
        }
        if (nome != null) {
            specifications.add(AlbumLiveSpecifications.likeArtistaAlbum(nome));
        }
        return specifications;
    }
    private Specification<AlbumLive> combineSpecifications(List<Specification<AlbumLive>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }

    @Override
    public List<AlbumLiveDTO> getAlbumLiveBy(Integer idAlbumLive, String titoloAlbumLive, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<AlbumLive>> specifications = createSpecifications(idAlbumLive, titoloAlbumLive, dataRilascio, brani, generi, nome);
        Specification<AlbumLive> combinedSpecification = combineSpecifications(specifications);

        List<AlbumLive> listaAlbumLive = albumLiveRepository.findAll((Sort) combinedSpecification);

        return listaAlbumLive.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }
}
