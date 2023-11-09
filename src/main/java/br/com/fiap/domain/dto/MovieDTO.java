package br.com.fiap.domain.dto;

import br.com.fiap.domain.entity.Movie;
import br.com.fiap.domain.service.GenreService;
import br.com.fiap.domain.service.MovieService;

import java.util.Objects;

public record MovieDTO(
        Long id,

        String title,

        boolean adult,

        GenreDTO genre,

        String originalLanguage
) {

    static MovieService service = new MovieService();
    static GenreService genreService = new GenreService();

    public static Movie of(MovieDTO dto) {

        // É nulo?
        if (Objects.isNull( dto )) return null;

        //Ele informou o id do produto?
        if (Objects.nonNull( dto.id )) return service.findById( dto.id );

        //Se não informou o Id é porque está salvando um novo Movie
        Movie m = new Movie();
        m.setId( null );

        //Se existir genre com o ID informado no Banco eu recupero todas as informações dele, mas
        // se não for informado o id do Genre e informou o name eu salvo um novo Genre
        var genre = Objects.nonNull( dto.genre ) && (Objects.nonNull( dto.genre.id() )) ?
                genreService.findById( dto.genre.id() )
                : !dto.genre.name().equalsIgnoreCase( "" ) ? genreService.persist( GenreDTO.of( dto.genre ) ) : null;

        m.setGenre( genre );

        m.setAdult( dto.adult );
        m.setTitle( dto.title );
        m.setOriginalLanguage( dto.originalLanguage );

        return m;
    }

    public static MovieDTO of(Movie m) {
        MovieDTO dto = new MovieDTO( m.getId(), m.getTitle(), m.isAdult(), GenreDTO.of( m.getGenre() ), m.getOriginalLanguage() );
        return dto;
    }

}
