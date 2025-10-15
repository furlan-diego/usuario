package com.project.usuario.business;

import com.project.usuario.business.converter.UsuarioConverter;
import com.project.usuario.business.dto.UsuarioDto;
import com.project.usuario.infrastructure.entity.Usuario;
import com.project.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDto(usuario);
    }
}

/*Para salvar um usuario, recebemos um objeto usuarioDto
, convertemos ele para a entidade Usuario, salvamos no banco de dados
 e retornamos o objeto salvo convertido novamente para UsuarioDto.
 */