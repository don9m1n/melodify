package com.dmk.melodify.domain.spotify.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchResponseDto {

    private String artistName; // 아티스트 이름
    private String title; // 노래 제목
    private String albumName; // 앨범 이름
    private String imageUrl; // 앨범 커버
    private Integer durationMs;  // 재생 시간 (ms)
    private String playTime; // 재생 시간 (분:초)
    private Boolean playable; // 재생 가능 여부

    public static SearchResponseDto toDto(String artistName, String title, String albumName, String imageUrl,
                                          Integer durationMs, String playtime, Boolean playable) {

        return SearchResponseDto.builder()
                .artistName(artistName)
                .title(title)
                .albumName(albumName)
                .imageUrl(imageUrl)
                .durationMs(durationMs)
                .playTime(playtime)
                .playable(playable)
                .build();
    }
}
