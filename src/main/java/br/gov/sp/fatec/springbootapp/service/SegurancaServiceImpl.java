package br.gov.sp.fatec.springbootapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;

@Service ("segurancaService")
public class SegurancaServiceImpl implements SegurancaService {

    @Autowired
    private AutorizacaoRepository autRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    /*serviceImpl-basicamente estamos na camada de negocio, onde estão as operações e regras de negocio.
    não deve se colocar isso no controller.
    Controller somente repassa para a camada de negocios, pega a resposta e devolve para a view.
    */

    /*transactional pode ser colocado em cima do metodo ou da classe, se colocado na classe, todos
    os metodos levarão essa operação*/

    /*Serviços usam varios repositorios, comum.*/

    @Transactional 
    public Usuario criarUsuario(String nome, String senha, String autorizacao) {

        Autorizacao aut= autRepo.findByNome(autorizacao);
        if(aut==null)
        {
            aut = new Autorizacao();
            aut.setNome(autorizacao);
            autRepo.save(aut);
        }

        
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSenha(senha);
        usuario.setAutorizacoes(new HashSet<Autorizacao>());
        usuario.getAutorizacoes().add(aut);
        usuarioRepo.save(usuario);

        return usuario;
    }

    @Override
    public List<Usuario>buscarTodosUsuarios()
    {
        return usuarioRepo.findAll();
    }
    
    @Override
    public Usuario buscarUsuarioPorId(Long id)
    {
        Optional<Usuario> usuarioOp= usuarioRepo.findById(id);
        if(usuarioOp.isPresent())
        {
            return usuarioOp.get();
        }
         throw new RuntimeException("usuario nao encontrado!");
    }

    @Override
    public Usuario buscarUsuarioPorNome(String nome)
    {
        Usuario usuario = usuarioRepo.findByNome(nome);
        if(usuario!=null)
        {
            return usuario;
        }
        throw new RuntimeException("usuario nao encontrado!");
    }
}