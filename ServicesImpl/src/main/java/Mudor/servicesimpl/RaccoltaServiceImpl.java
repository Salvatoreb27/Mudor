package Mudor.servicesimpl;

import Mudor.DTO.AlbumLiveDTO;
import Mudor.DTO.RaccoltaDTO;
import Mudor.entity.AlbumLive;
import Mudor.entity.ArtistaMusicale;
import Mudor.entity.Raccolta;
import Mudor.entity.spec.AlbumLiveSpecifications;
import Mudor.entity.spec.RaccoltaSpecifications;
import Mudor.repository.ArtistaMusicaleRepository;
import Mudor.repository.RaccoltaRepository;
import Mudor.services.ArtistaMusicaleService;
import Mudor.services.RaccoltaService;
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
public class RaccoltaServiceImpl implements RaccoltaService {

    @Autowired
    private RaccoltaRepository raccoltaRepository;

    @Autowired
    private ArtistaMusicaleRepository artistaMusicaleRepository;

    @Override
    public RaccoltaDTO mapTODTO(Raccolta raccolta) {
        RaccoltaDTO raccoltaDTO = RaccoltaDTO.builder()
                .titoloRaccolta(raccolta.getTitoloRaccolta())
                .dataRilascio(raccolta.getDataRilascio())
                .brani(raccolta.getBrani())
                .generi(raccolta.getGeneri())
                .idArtistaMusicale(raccolta.getArtistaMusicale().getIdArtista())
                .build();
        return raccoltaDTO;
    }

    @Override
    public List<RaccoltaDTO> mapTODTOList(List<Raccolta> raccoltaList) {
        return raccoltaList.stream()
                .map((Raccolta raccolta) -> this.mapTODTO(raccolta))
                .collect(Collectors.toList());
    }

    @Override
    public Raccolta mapToEntity(RaccoltaDTO raccoltaDTO) {
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(raccoltaDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        Raccolta raccolta = Raccolta.builder()
                .titoloRaccolta(raccoltaDTO.getTitoloRaccolta())
                .dataRilascio(raccoltaDTO.getDataRilascio())
                .brani(raccoltaDTO.getBrani())
                .generi(raccoltaDTO.getGeneri())
                .artistaMusicale(artistaMusicale)
                .build();
        return null;
    }

    @Override
    public List<Raccolta> mapTOEntityList(List<RaccoltaDTO> raccoltaDTOList) {
        return raccoltaDTOList.stream()
                .map((RaccoltaDTO raccoltaDTO) -> this.mapToEntity(raccoltaDTO))
                .collect(Collectors.toList());
    }

    @Override
    public List<RaccoltaDTO> getDTOs() {
        List<Raccolta> raccoltaList = new ArrayList<>(raccoltaRepository.findAll());
        return mapTODTOList(raccoltaList);
    }

    @Override
    public RaccoltaDTO getDTO(Integer id) {
        Optional<Raccolta> raccolta = raccoltaRepository.findById(id);
        if (raccolta.isPresent()) {
            return mapTODTO(raccolta.get());
        } else {
            return null;
        }
    }

    @Override
    public void add(RaccoltaDTO raccoltaDTO) {
        Raccolta raccolta = mapToEntity(raccoltaDTO);
        raccoltaRepository.save(raccolta);
    }

    @Override
    public void update(RaccoltaDTO raccoltaDTO, Integer id) {
        ArtistaMusicale artistaMusicale = artistaMusicaleRepository.findById(raccoltaDTO.getIdArtistaMusicale()).orElseThrow(() -> new RuntimeException("Artista musicale non trovato"));
        Raccolta raccoltaRicercata = raccoltaRepository.findById(id).orElseThrow(() -> new RuntimeException("La raccolta ricercata non è presente"));
        raccoltaRicercata.setTitoloRaccolta(raccoltaDTO.getTitoloRaccolta() == null ? raccoltaRicercata.getTitoloRaccolta() : raccoltaDTO.getTitoloRaccolta());
        raccoltaRicercata.setDataRilascio(raccoltaDTO.getDataRilascio() == null ? raccoltaRicercata.getDataRilascio() : raccoltaDTO.getDataRilascio());
        raccoltaRicercata.setGeneri(raccoltaDTO.getGeneri() == null ? raccoltaRicercata.getGeneri() : raccoltaDTO.getGeneri());
        raccoltaRicercata.setBrani(raccoltaDTO.getBrani() == null ? raccoltaRicercata.getBrani() : raccoltaDTO.getBrani());
        raccoltaRicercata.setArtistaMusicale(artistaMusicale == null ? raccoltaRicercata.getArtistaMusicale() : artistaMusicale);
        raccoltaRepository.save(raccoltaRicercata);
    }

    @Override
    public void delete(Integer id) {
        if (raccoltaRepository.existsById(id)) {
            raccoltaRepository.deleteById(id);
        }
    }

    private List<Specification<Raccolta>> createSpecifications(Integer idRaccolta, String titoloRaccolta, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<Raccolta>> specifications = new ArrayList<>();
        if (idRaccolta != null) {
            specifications.add(RaccoltaSpecifications.likeIdRaccolta(idRaccolta));
        }
        if (titoloRaccolta != null) {
            specifications.add(RaccoltaSpecifications.likeTitoloRaccolta(titoloRaccolta));
        }
        if (dataRilascio != null) {
            specifications.add(RaccoltaSpecifications.likeDataRilascio(dataRilascio));
        }
        if (brani != null) {
            specifications.add(RaccoltaSpecifications.likeBrani(brani));
        }
        if (generi != null) {
            specifications.add(RaccoltaSpecifications.likeGeneri(generi));
        }
        if (nome != null) {
            specifications.add(RaccoltaSpecifications.likeArtistaAlbum(nome));
        }
        return specifications;
    }
    private Specification<Raccolta> combineSpecifications(List<Specification<Raccolta>> specifications) {
        return specifications.stream().reduce(Specification::and).orElse(null);
    }

    @Override
    public List<RaccoltaDTO> getRaccoltaBy(Integer idRaccolta, String titoloRaccolta, Date dataRilascio, List<String> brani, List<String> generi, String nome) {
        List<Specification<Raccolta>> specifications = createSpecifications(idRaccolta, titoloRaccolta, dataRilascio, brani, generi, nome);
        Specification<Raccolta> combinedSpecification = combineSpecifications(specifications);

        List<Raccolta> listaRaccolte = raccoltaRepository.findAll((Sort) combinedSpecification);

        return listaRaccolte.stream()
                .map(this::mapTODTO)
                .collect(Collectors.toList());
    }
}
