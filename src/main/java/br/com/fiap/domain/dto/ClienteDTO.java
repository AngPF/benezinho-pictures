package br.com.fiap.domain.dto;

import br.com.fiap.domain.entity.Cliente;
import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.domain.service.ClienteService;

import java.util.Objects;

public record ClienteDTO(Long id, String name) {

    private static ClienteService service = new ClienteService();

    public static Cliente of(ClienteDTO dto) {
        // É nulo?
        if (Objects.isNull( dto )) return null;

        //Ele informou o id do produto?
        if (Objects.nonNull( dto.id )) return service.findById( dto.id );

        //Se não informou o Id é porque está salvando um novo Movie
        Cliente g = new Cliente();
        g.setId( null );
        g.setName( dto.name );

        return g;
    }


    public static ClienteDTO of(Cliente g) {
        return new ClienteDTO( g.getId(), g.getName() );
    }

}
