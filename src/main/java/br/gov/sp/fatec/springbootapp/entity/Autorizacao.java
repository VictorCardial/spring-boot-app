package br.gov.sp.fatec.springbootapp.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="aut_autorizacao")
public class Autorizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aut_id")
    private Long id;

    @Column(name="aut_nome")
    private String nome;

    /*mapeando a tabela uau_usuario_autorizacao que liga autorizacao a usuario n:n*/
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "autorizacoes") //atributo em usuario onde é mapeado o relacionamento.
    private Set<Usuario> usuarios;

    /*get/set id*/
    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id=id;
    }

    /*get/set nome*/ 
    public String getNome()
    {
        return this.nome;
    }

    public void setNome(String nome)
    {
        this.nome=nome;
    }
    /*get/set usuario*/
    public Set<Usuario> getUsuarios()
    {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario>usuarios)
    {
        this.usuarios=usuarios;
    }
}