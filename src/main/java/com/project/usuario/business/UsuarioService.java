package com.project.usuario.business;

import com.project.usuario.business.converter.UsuarioConverter;
import com.project.usuario.business.dto.EnderecoDto;
import com.project.usuario.business.dto.TelefoneDto;
import com.project.usuario.business.dto.UsuarioDto;
import com.project.usuario.infrastructure.entity.Endereco;
import com.project.usuario.infrastructure.entity.Telefone;
import com.project.usuario.infrastructure.entity.Usuario;
import com.project.usuario.infrastructure.exceptions.ConflictException;
import com.project.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.project.usuario.infrastructure.repository.EnderecoRepository;
import com.project.usuario.infrastructure.repository.TelefoneRepository;
import com.project.usuario.infrastructure.repository.UsuarioRepository;
import com.project.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder; // para fazer a criptografia da senha
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDto buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDto(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(
                    () -> new ResourceNotFoundException("Email nao encontrado " + email)
                            )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("email não encontardo" + email);
        }
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDto atualizaDadosUsuario(String token, UsuarioDto dto) {
        //Aqui buscamos o email do usuario atraves do token JWT (tirando a obrigatoriedade de passar o email na requisição)
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //Criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //Aqui buscamos os dados do usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não encontrado"));

        //Mesclou os dados que recebemos na requisição Dto com os dados que já existem no banco de dados
        //e retornamos o usuario atualizado
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        //Salvou os dados do usuario convertido e depois pegou o retorno e converteu para UsuarioDto.
        return usuarioConverter.paraUsuarioDto(usuarioRepository.save(usuario));
    }

    public EnderecoDto atualizaEndereco(Long idEndereco, EnderecoDto enderecoDto) {

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDto, entity);

        return usuarioConverter.paraEnderecoDto(enderecoRepository.save(endereco));
    }

    public TelefoneDto atualizaTelefone(Long idTelefone, TelefoneDto dto) {

        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDto(telefoneRepository.save(telefone));
    }
}

/* linhas 17 a 20 metodo salvaUsuario
Para salvar um usuario, recebemos um objeto usuarioDto
, convertemos ele para a entidade Usuario, salvamos no banco de dados
 e retornamos o objeto salvo convertido novamente para UsuarioDto.
 */