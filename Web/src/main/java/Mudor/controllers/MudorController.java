package Mudor.controllers;

import Mudor.servicesimpl.MudorFinderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.net.Proxy;


@RestController
@RequestMapping("/api/v1/mudor")
public class MudorController {

    @Autowired
    MudorFinderServiceImpl mudorFinderServiceImpl;


    @GetMapping("/searchSingerM")
    public Object searchSingerM(@RequestParam String name) {
        return mudorFinderServiceImpl.searchSingerMusicBrainz(name);
    }

    @GetMapping("/getPageIdOfSingerM")
    public Object getPageIdOfSingerM(@RequestParam String name) {
        return mudorFinderServiceImpl.getSingerIdMusicBrainz(name);
    }


    @GetMapping("/searchSingerPageM")
    public Object getPageSingerM(@RequestParam String name) {
        return mudorFinderServiceImpl.searchSingerPageMusicBrainz(name);
    }

    @GetMapping("/searchAlbumInStudioPageM")
    public Object searchAlbumInStudioPageM(@RequestParam String name) {
        return mudorFinderServiceImpl.searchAlbumInStudioPageMusicBrainz(name);
    }


    @GetMapping("/getListOfAlbumsInStudioOfArtistM")
    public Object getListOfAlbumsInStudioOfArtistM(@RequestParam String name) {
        return mudorFinderServiceImpl.extractAlbumTitlesMusicBrainz(name);
    }
    @GetMapping("/releaseConstruct")
    public ResponseEntity<String> releaseConstruct(@RequestParam String title, @RequestParam String artistName) {
        return mudorFinderServiceImpl.releaseConstruct(title, artistName);
    }
    @GetMapping("/mudorConstruct")
    public ResponseEntity<String> mudorConstruct(@RequestParam String name) {
       return mudorFinderServiceImpl.mudorConstruct(name);
    }


    @GetMapping("/constructTracksForAlbumsOfArtist")
    public void constructTracksForAlbumsOfArtist(@RequestParam String name) {
        mudorFinderServiceImpl.constructTracksForArtist(name);
    }

    @GetMapping("/constructCoverForAlbumsOfArtist")
    public void constructCoverForAlbumsOfArtist(@RequestParam String name) {
        mudorFinderServiceImpl.constructCoverArtForArtist(name);
    }
}



