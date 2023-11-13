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

        // É nulo?
        if (Objects.isNull( dto )) return null;

        //Ele informou o id do produto?
        if (Objects.nonNull( dto.id )) return service.findById( dto.id );

        //Se não informou o Id é porque está salvando um novo Bicicleta
        Bicicleta m = new Bicicleta();
        m.setId( null );

        //Se existir cliente com o ID informado no Banco eu recupero todas as informações dele, mas
        // se não for informado o id do cliente e informou o name eu salvo um novo cliente
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
