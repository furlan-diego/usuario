package com.project.usuario.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDto> enderecos;
    private List<TelefoneDto> telefones;

}
