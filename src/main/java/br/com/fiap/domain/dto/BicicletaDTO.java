package br.com.fiap.domain.dto;

import br.com.fiap.domain.entity.Bicicleta;
import br.com.fiap.domain.service.ClienteService;
import br.com.fiap.domain.service.BicicletaService;

import java.util.Objects;

public record BicicletaDTO(
        Long id,

        String descricao,

        ClienteDTO cliente
) {

    static BicicletaService service = new BicicletaService();
    static ClienteService clienteService = new ClienteService();

    public static Bicicleta of(BicicletaDTO dto) {

        if (Objects.isNull( dto )) return null;

        if (Objects.nonNull( dto.id )) return service.findById( dto.id );

        Bicicleta m = new Bicicleta();
        m.setId( null );

        var cliente = Objects.nonNull( dto.cliente ) && (Objects.nonNull( dto.cliente.id() )) ?
                clienteService.findById( dto.cliente.id() )
                : !dto.cliente.name().equalsIgnoreCase( "" ) ? clienteService.persist( ClienteDTO.of( dto.cliente ) ) : null;

        m.setCliente( cliente );

        m.setDescricao( dto.descricao );

        return m;
    }

    public static BicicletaDTO of(Bicicleta m) {
        BicicletaDTO dto = new BicicletaDTO( m.getId(), m.getDescricao(), ClienteDTO.of( m.getCliente() ));
        return dto;
    }

}
