package br.com.fiap.domain.service;

import br.com.fiap.domain.entity.Cliente;
import br.com.fiap.domain.repository.ClienteRepository;

import java.util.List;
import java.util.Objects;

public class ClienteService implements Service<Cliente, Long> {

    ClienteRepository repo = ClienteRepository.build();

    @Override
    public List<Cliente> findAll() {
        return repo.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        return repo.findById( id );
    }

    @Override
    public Cliente persist(Cliente cliente) {
        var g = repo.findByName( cliente.getName() );
        if (Objects.nonNull( g )){
            System.err.println("Já existe genero cadastrado com o mesmo nome: " + g.getName());
            return g;
        }
        return repo.persist( cliente );
    }
}
