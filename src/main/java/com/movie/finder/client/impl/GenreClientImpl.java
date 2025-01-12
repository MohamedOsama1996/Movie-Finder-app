package com.movie.finder.client.impl;

import com.movie.finder.client.GenreClient;
import com.movie.finder.client.response.ClientGenre;
import com.movie.finder.client.response.TmdbGenreResponse;
import com.movie.finder.exception.ErrorCode;
import com.movie.finder.exception.MovieFinderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class GenreClientImpl implements GenreClient {

    @Value("${tmdb.api.key}")
    public String TMDB_API_KEY;

    @Value("${tmdb.api.url}")
    public String TMDB_API_BASE_URL;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<ClientGenre> getGenres() {

        try {
            String url = String.format("%s/genre/movie/list?api_key=%s", TMDB_API_BASE_URL, TMDB_API_KEY);
            ResponseEntity<TmdbGenreResponse> tmdbResponse = restTemplate.exchange(url, HttpMethod.GET, null, TmdbGenreResponse.class);
            return tmdbResponse.getBody().getGenres();
        }catch (Exception ex){
            throw new MovieFinderException(ErrorCode.MF_CLIENT_GENRE_500, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
