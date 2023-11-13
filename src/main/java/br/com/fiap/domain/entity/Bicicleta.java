package br.com.fiap.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Bicicleta {

    private Long id;

    private String descricao;

    private Cliente cliente;

}