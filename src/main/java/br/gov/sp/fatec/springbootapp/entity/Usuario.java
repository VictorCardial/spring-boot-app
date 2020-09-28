package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.gov.sp.fatec.springbootapp.controller.View;


@Entity
@Table(name="usr_usuario")
public class Usuario {
    
    @JsonView(View.UsuarioCompleto.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usr_id")
    private Long id;
    
    @JsonView({View.UsuarioResumo.class,View.AutorizacaoResumo.class})
    @Column(name="usr_nome")
    private String nome;

    @Column(name="usr_senha")
    private String senha;

    /*mapeando a tabela uau_usuario_autorizacao de ligacao de usuario e autorizacao n:n*/

    @JsonView(View.UsuarioResumo.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "uau_usuario_autorizacao",
    joinColumns = { @JoinColumn(name = "usr_id")},
    inverseJoinColumns = {@JoinColumn(name="aut_id")})
    private Set<Autorizacao> autorizacoes;

    /*get/set -id*/ 
    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id=id;
    }
    /*get/set -nome*/
    public String getNome()
    {
        return this.nome;
    }

    public void setNome(String nome)
    {
        this.nome=nome;
    }

    /*get/set -senha*/
    public String getSenha()
    {
        return this.senha;
    }

    public void setSenha(String senha)
    {
        this.senha=senha;
    }

    /*get/set autorizacao*/ 
    public Set<Autorizacao> getAutorizacoes()
    {
        return this.autorizacoes;
    }

    public void setAutorizacoes(Set<Autorizacao>autorizacoes)
    {
        this.autorizacoes=autorizacoes;
    }
}