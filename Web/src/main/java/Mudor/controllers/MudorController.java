package Mudor.controllers;

import Mudor.servicesimpl.MudorFinderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.net.Proxy;


/**
 * Questa classe gestisce le richieste relative a Mudor attraverso API REST.
 * Fornisce endpoint per la ricerca di informazioni sugli artisti e i loro album.
 */
@RestController
@RequestMapping("/api/v1/mudor")
public class MudorController {

    @Autowired
    MudorFinderServiceImpl mudorFinderServiceImpl;


    /**
     * Ricerca un cantante su MusicBrainz.
     *
     * @param name il nome del cantante da cercare
     * @return l'oggetto risultato della ricerca
     */
    @GetMapping("/searchSingerM")
    public Object searchSingerM(@RequestParam String name) {
        return mudorFinderServiceImpl.searchSingerMusicBrainz(name);
    }

    /**
     * Ottiene l'ID di un cantante su MusicBrainz.
     *
     * @param name il nome del cantante di cui ottenere l'ID
     * @return l'ID del cantante su MusicBrainz
     */
    @GetMapping("/getPageIdOfSingerM")
    public Object getPageIdOfSingerM(@RequestParam String name) {
        return mudorFinderServiceImpl.getSingerIdMusicBrainz(name);
    }


    /**
     * Cerca la pagina di un cantante su MusicBrainz.
     *
     * @param name il nome del cantante di cui cercare la pagina
     * @return l'oggetto risultato della ricerca della pagina del cantante
     */
    @GetMapping("/searchSingerPageM")
    public Object getPageSingerM(@RequestParam String name) {
        return mudorFinderServiceImpl.searchSingerPageMusicBrainz(name);
    }


    /**
     * Cerca gli album in studio di un artista su MusicBrainz.
     *
     * @param name il nome dell'artista di cui cercare gli album in studio
     * @return l'oggetto risultato della ricerca degli album in studio dell'artista
     */
    @GetMapping("/searchAlbumInStudioPageM")
    public Object searchAlbumInStudioPageM(@RequestParam String name) {
        return mudorFinderServiceImpl.searchAlbumInStudioPageMusicBrainz(name);
    }


    /**
     * Ottiene la lista degli album in studio di un artista su MusicBrainz.
     *
     * @param name il nome dell'artista di cui ottenere la lista degli album in studio
     * @return la lista degli album in studio dell'artista
     */
    @GetMapping("/getListOfAlbumsInStudioOfArtistM")
    public Object getListOfAlbumsInStudioOfArtistM(@RequestParam String name) {
        return mudorFinderServiceImpl.extractReleasesTitlesMusicBrainz(name);
    }

    /**
     * Costruisce una release.
     *
     * @param title      il titolo della release
     * @param artistName il nome dell'artista associato alla release
     * @return una ResponseEntity contenente il risultato della costruzione della release
     */
    @GetMapping("/releaseConstruct")
    public ResponseEntity<String> releaseConstruct(@RequestParam String title, @RequestParam String artistName) {
        return mudorFinderServiceImpl.releaseConstruct(title, artistName);
    }

    /**
     * Costruisce una discografia.
     *
     * @param name il nome dell'artista di cui ottenere ed aggiungere la discografia.
     * @return una ResponseEntity contenente il risultato della costruzione ed aggiunta persistente della discografia
     */
    @GetMapping("/discographyConstruct")
    public ResponseEntity<String> discographyConstruct(@RequestParam String name) {
       return mudorFinderServiceImpl.discographyConstruct(name);
    }
}



