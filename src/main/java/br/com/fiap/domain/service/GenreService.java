package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Genre;
import br.com.fiap.domain.repository.GenreRepository;

import java.util.List;
import java.util.Objects;

public class GenreService implements Service<Genre, Long> {

    GenreRepository repo = GenreRepository.build();

    @Override
    public List<Genre> findAll() {
        return repo.findAll();
    }

    @Override
    public Genre findById(Long id) {
        return repo.findById( id );
    }

    @Override
    public Genre persist(Genre genre) {
        //Não pode ter Genero com mesmo nome
        var g = repo.findByName( genre.getName() );
        if (Objects.nonNull( g )){
            System.err.println("Já existe genero cadastrado com o mesmo nome: " + g.getName());
            return g;
        }
        return repo.persist( genre );
    }
}
