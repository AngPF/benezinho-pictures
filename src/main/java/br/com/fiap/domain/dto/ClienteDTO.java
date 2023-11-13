package br.com.fiap.domain.dto;

import br.com.fiap.domain.entity.Cliente;
import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.domain.service.ClienteService;

import java.util.Objects;

public record ClienteDTO(Long id, String name) {

    private static ClienteService service = new ClienteService();

    public static Cliente of(ClienteDTO dto) {

        if (Objects.isNull( dto )) return null;

        if (Objects.nonNull( dto.id )) return service.findById( dto.id );

        Cliente g = new Cliente();
        g.setId( null );
        g.setName( dto.name );

        return g;
    }


    public static ClienteDTO of(Cliente g) {
        return new ClienteDTO( g.getId(), g.getName() );
    }

}
