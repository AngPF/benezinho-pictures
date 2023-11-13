package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.domain.repository.BicicletaRepository;

import java.util.List;
import java.util.Objects;

public class BicicletaService implements Service<Bicicleta, Long> {

    BicicletaRepository repo = BicicletaRepository.build();

    @Override
    public List<Bicicleta> findAll() {
        return repo.findAll();
    }

    @Override
    public Bicicleta findById(Long id) {
        return repo.findById( id );
    }

    @Override
    public Bicicleta persist(Bicicleta bicicleta) {
        var m = repo.findByName( bicicleta.getDescricao() );
        if (Objects.nonNull( m )) {
            System.err.println( "Já existe Bicicleta cadastrado com a mesmo Descrição: " + m.getDescricao() );
            return m;
        }
        return repo.persist( bicicleta );
    }
}
