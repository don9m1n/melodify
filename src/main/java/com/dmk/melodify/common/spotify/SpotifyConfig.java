package com.dmk.melodify.common.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyApi.Builder;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SpotifyConfig {

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    // accessToken을 담은 spotifyApi 객체 빈 등록
    @Bean
    public SpotifyApi spotifyApi() {
        return new SpotifyApi.Builder()
                .setAccessToken(accessToken())
                .build();
    }

    public String accessToken() {
        SpotifyApi spotifyApi = new Builder().setClientId(clientId).setClientSecret(clientSecret).build();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            return "error";
        }
    }
}
