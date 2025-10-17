package com.project.usuario.business.converter;

import com.project.usuario.business.dto.EnderecoDto;
import com.project.usuario.business.dto.TelefoneDto;
import com.project.usuario.business.dto.UsuarioDto;
import com.project.usuario.infrastructure.entity.Endereco;
import com.project.usuario.infrastructure.entity.Telefone;
import com.project.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecoDtos) {
        return enderecoDtos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDto enderecoDto) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .complemento(enderecoDto.getComplemento())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .cep(enderecoDto.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefones(List<TelefoneDto> telefoneDto) {
        return telefoneDto.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDto telefoneDto) {
        return Telefone.builder()
                .numero(telefoneDto.getNumero())
                .ddd(telefoneDto.getDdd())
                .build();
    }


    public UsuarioDto paraUsuarioDto(Usuario usuarioDto) {
        return UsuarioDto.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEnderecoDto(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefonesDto(usuarioDto.getTelefones()))
                .build();
    }

    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> enderecoDtos) {
        return enderecoDtos.stream().map(this::paraEndereco).toList();
    }

    public EnderecoDto paraEndereco(Endereco enderecoDto) {
        return EnderecoDto.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .complemento(enderecoDto.getComplemento())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .cep(enderecoDto.getCep())
                .build();
    }

    public List<TelefoneDto> paraListaTelefonesDto(List<Telefone> telefoneDto) {
        return telefoneDto.stream().map(this::paraTelefoneDto).toList();
    }

    public TelefoneDto paraTelefoneDto(Telefone telefoneDto) {
        return TelefoneDto.builder()
                .numero(telefoneDto.getNumero())
                .ddd(telefoneDto.getDdd())
                .build();
    }
    //metodo de update só do usuario. Nele verificamos se o campo do dto é nulo, se for nulo mantemos o valor antigo, se não pegamos o valor novo
    public Usuario updateUsuario(UsuarioDto usuarioDto, Usuario entity){
        return Usuario.builder()
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : entity.getSenha())
                .email(entity.getEmail() != null ? usuarioDto.getEmail() : entity.getEmail()
                )
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

}
