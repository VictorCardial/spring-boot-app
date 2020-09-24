package br.gov.sp.fatec.springbootapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.springbootapp.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
    /*criacao de querys*/
    public List<Usuario> findByNomeContainsIgnoreCase(String nome);

    public Usuario findByNome(String nome);

    public Usuario findByNomeAndSenha(String nome,String senha);

    public List<Usuario> findByAutorizacoesNome(String autorizacao);


}