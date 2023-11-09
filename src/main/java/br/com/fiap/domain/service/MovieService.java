package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Movie;
import br.com.fiap.domain.repository.MovieRepository;

import java.util.List;
import java.util.Objects;

public class MovieService implements Service<Movie, Long> {

    MovieRepository repo = MovieRepository.build();

    @Override
    public List<Movie> findAll() {
        return repo.findAll();
    }

    @Override
    public Movie findById(Long id) {
        return repo.findById( id );
    }

    @Override
    public Movie persist(Movie movie) {
        //Não pode ter Genero com mesmo Título
        var m = repo.findByName( movie.getTitle() );
        if (Objects.nonNull( m )) {
            System.err.println( "Já existe Filme cadastrado com o mesmo Título: " + m.getTitle() );
            return m;
        }
        return repo.persist( movie );
    }
}
