package com.dmk.melodify.domain.spotify.controller;

import com.dmk.melodify.domain.spotify.dto.SearchResponseDto;
import com.dmk.melodify.domain.spotify.service.SpotifyService;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/spotify")
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        List<SearchResponseDto> searchList = spotifyService.search(query);

        model.addAttribute("searchList", searchList);
        return "spotify/search";
    }

}
