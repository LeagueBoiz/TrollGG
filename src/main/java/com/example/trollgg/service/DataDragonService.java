package com.example.trollgg.service;

import com.example.trollgg.error.ExternalApiCallException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DataDragonService {
    private static final String DATA_DRAGON_URL = "https://ddragon.leagueoflegends.com/cdn/";
    private static final String DATA_DRAGON_VERSION = "https://ddragon.leagueoflegends.com/api/versions.json";

    public String getLatestDataDragonVersion() {
        try {
            List<String> versionList = new RestTemplate()
                    .exchange(DATA_DRAGON_VERSION, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){})
                    .getBody();

            return Objects.requireNonNull(versionList).stream()
                    .filter(Objects::nonNull)
                    .toList()
                    .get(0);

        } catch (Exception e) {
            throw new ExternalApiCallException(e.getMessage());
        }
    }

    public String getProfileUrl(int profileIconId) {
        return DATA_DRAGON_URL + getLatestDataDragonVersion() + "/img/profileicon/" + profileIconId + ".png";
    }
}
