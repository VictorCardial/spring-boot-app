package br.gov.sp.fatec.springbootapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;

@SpringBootTest
@Transactional
@Rollback
class SpringBootAppApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private AutorizacaoRepository autRepo;
    
	@Test
	void contextLoads() {
    }
    
    @Test
    void testaInsercao()
    {
        Usuario usuario=new Usuario();
        usuario.setNome("Cardial");
        usuario.setSenha("123");
        usuario.setAutorizacoes(new HashSet<Autorizacao>());
        Autorizacao aut=new Autorizacao();
        aut.setNome("simple_user");
        autRepo.save(aut);
        usuario.getAutorizacoes().add(aut);
        usuarioRepo.save(usuario);
        assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
    }

    @Test  
    void testaAutorizacao()
    {
        Usuario usuario = usuarioRepo.findById(2L).get();
        assertEquals("role_admin",usuario.getAutorizacoes().iterator().next().getNome());
    }

    @Test
    void testaUsuario()
    {
        Autorizacao aut= autRepo.findById(1L).get();
        assertEquals("victor",aut.getUsuarios().iterator().next().getNome());
    }

    
}
