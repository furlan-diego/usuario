package com.project.usuario.business;

import com.project.usuario.business.converter.UsuarioConverter;
import com.project.usuario.business.dto.UsuarioDto;
import com.project.usuario.infrastructure.entity.Usuario;
import com.project.usuario.infrastructure.exceptions.ConflictException;
import com.project.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.project.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder; // para fazer a criptografia da senha

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto) {
        emailExiste(usuarioDto.getEmail());
        usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha())); // criptografa a senha antes de salvar

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDto(usuario);
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email nao encontrado " + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}

/* linhas 17 a 20 metodo salvaUsuario
Para salvar um usuario, recebemos um objeto usuarioDto
, convertemos ele para a entidade Usuario, salvamos no banco de dados
 e retornamos o objeto salvo convertido novamente para UsuarioDto.
 */