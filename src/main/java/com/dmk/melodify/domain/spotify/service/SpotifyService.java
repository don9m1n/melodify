package com.dmk.melodify.domain.spotify.service;

import com.dmk.melodify.common.AppConfig;
import com.dmk.melodify.common.spotify.SpotifyConfig;
import com.dmk.melodify.domain.spotify.dto.SearchResponseDto;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.AlbumGroup;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApi spotifyApi;

    public List<SearchResponseDto> search(String query)  {

        List<SearchResponseDto> searchResponseDtoList = new ArrayList<>();

        try {
            SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query)
                    .setHeader("Accept-Language", "ko-KR") // 결과값을 한글로 받기
                    .market(CountryCode.KR)
                    .limit(30)
                    .offset(0)
                    .build();

            Paging<Track> searchResult = searchTracksRequest.execute();
            Track[] tracks = searchResult.getItems();
            for (Track track : tracks) {

                AlbumSimplified album = track.getAlbum();
                ArtistSimplified[] artists = album.getArtists();

                String title = track.getName(); // 노래 제목
                String artistName = artists[0].getName(); // 아티스트 이름
                String albumName = album.getName(); // 앨범 이름

                Integer durationMs = track.getDurationMs();// 노래 재생 시간 (ms)
                String playtime = msToMinute(durationMs); // 노래 재생 시간 (분:초)
                Boolean playable = track.getIsPlayable(); // 노래 재생 가능 여부

                Image[] images = album.getImages(); // 앨범 이미지 목록
                String imageUrl = (images.length > 0) ? images[0].getUrl() : "NO_IMAGE";

                searchResponseDtoList.add(
                        SearchResponseDto.toDto(artistName, title, albumName, imageUrl, durationMs, playtime,
                                playable));
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }

        return searchResponseDtoList;
    }

    private String msToMinute(int ms) {

        long minutes = (ms / 1000) / 60;
        long seconds = (ms / 1000) % 60;

        return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
    }

}
