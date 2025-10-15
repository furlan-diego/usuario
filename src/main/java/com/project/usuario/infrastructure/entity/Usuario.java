package com.project.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//O Lombok fornece anotações para reduzir o código boilerplate em Java, como getters, setters, construtores, etc.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> enderecos;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    //login com senha
    @Override
    public String getPassword() {
        return senha;
    }

    //login com e mail
    @Override
    public String getUsername() {
        return email;
    }
}

/* UserDetails é uma classe do spring security que representa um usuário no sistema,
 fornecendo métodos para obter informações sobre o usuário, como nome de usuário, senha e autoridades (roles).

Quando implementamos essa interface na nossa classe Usuario, estamos dizendo ao Spring Security
 como obter essas informações para autenticar e autorizar o usuário.

Quando implementamos o UserDetails precisamos implementar 3 métodos obrigatórios:
 getAuthorities, getPassword e getUsername.

Neste exemplo, não estamos usando roles, portanto o login será feito somente com email e senha.*/