package br.com.fiap.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Movie {

    private Long id;

    private String title;

    private boolean adult;

    private Genre genre;

    private String originalLanguage;

}