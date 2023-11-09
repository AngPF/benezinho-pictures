package br.com.fiap.domain.dto;

import br.com.fiap.domain.entity.Genre;
import br.com.fiap.domain.entity.Movie;
import br.com.fiap.domain.service.GenreService;

import java.util.Objects;

public record GenreDTO(Long id, String name) {

    private static GenreService service = new GenreService();

    public static Genre of(GenreDTO dto) {
        // É nulo?
        if (Objects.isNull( dto )) return null;

        //Ele informou o id do produto?
        if (Objects.nonNull( dto.id )) return service.findById( dto.id );

        //Se não informou o Id é porque está salvando um novo Movie
        Genre g = new Genre();
        g.setId( null );
        g.setName( dto.name );

        return g;
    }


    public static GenreDTO of(Genre g) {
        return new GenreDTO( g.getId(), g.getName() );
    }

}
