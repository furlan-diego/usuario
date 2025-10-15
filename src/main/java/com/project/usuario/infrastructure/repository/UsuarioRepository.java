package com.project.usuario.infrastructure.repository;

import com.project.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Verifica se o email já existe no banco de dados, retornando true ou false
    boolean existsByEmail(String email);
    //Usamos Optional para evitar o erro NullPointerException
    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}


/* O Optional serve basicamente para evitarmos o retorno de informações nulas,
ex: Se ele for no banco de dados buscar usuario por e mail e esse usuario não existir,
vai estourar um erro chamado nullPointerException que quebra nosso código.
O Optional vai trarar esse erro evitando que quebre nosso código. */