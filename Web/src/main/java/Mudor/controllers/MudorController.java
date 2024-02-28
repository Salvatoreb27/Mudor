package Mudor.controllers;

import Mudor.servicesimpl.MudorFinderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getAlbumInStudioInfo")
    public Object getAlbumInStudioInfo(@RequestParam String name) {
        return mudorFinderServiceImpl.getAlbumsInfoMusicBrainz(name);
    }

    @GetMapping("/getAllFirstReleases")
    public Object getAllFirstReleases(@RequestParam String name) {
        return mudorFinderServiceImpl.getAlbumReleasesOfReleaseGroup(name);
    }

    @GetMapping("/mudorConstruct")
    public void mudorConstruct(@RequestParam String name) {
        mudorFinderServiceImpl.mudorConstruct(name);
    }

    @GetMapping("/constructTracksForAlbumsOfArtist")
    public void constructTracksForAlbumsOfArtist(@RequestParam String name) {
        mudorFinderServiceImpl.constructTracksForAlbumsOfArtist(name);
    }

//    @PostMapping("/addAlbumsInStudioToMudor")
//    public void addAlbumsInStudioToMudor(@RequestParam String name) {
//        mudorFinderServiceImpl.addAlbumsInStudioToMudorDB(name);
//    }
}


