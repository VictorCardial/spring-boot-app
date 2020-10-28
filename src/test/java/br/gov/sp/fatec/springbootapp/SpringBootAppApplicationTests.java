package br.gov.sp.fatec.springbootapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutorizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@SpringBootTest
@Transactional
//@Rollback
class SpringBootAppApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private AutorizacaoRepository autRepo;

    @Autowired
    private SegurancaService segService;


// banco de dados h2 configurado a parte para rodar os testes...
    @BeforeAll
    static void init(@Autowired JdbcTemplate jdbcTemplate)
    {
        jdbcTemplate.update("insert into usr_usuario(usr_nome,usr_senha) values (?,?)",
        "victor","boasenha");
        jdbcTemplate.update("insert into aut_autorizacao(aut_nome) values (?)",
        "role_admin");
        jdbcTemplate.update("insert into uau_usuario_autorizacao(usr_id,aut_id) values (?,?)",
        1L,1L);
    }
    
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
    void testaInsercaoAutorizacao()
    {
        Usuario usuario=new Usuario();
        usuario.setNome("Cardial2");
        usuario.setSenha("123");
        usuarioRepo.save(usuario);
        Autorizacao aut=new Autorizacao();
        aut.setNome("simple_user2");
        aut.setUsuarios(new HashSet<Usuario>());
        aut.getUsuarios().add(usuario);
        autRepo.save(aut);
        assertNotNull(aut.getUsuarios().iterator().next().getId());
    }
    /*nocoes de quem manda no relacionamento, no caso, usuario, onde a tabela uau_usuario_autorizacao foi mapeada
    quando instancio a lista de autorizacoes pelo usuario, ele salva na tabela de ligacao
    mas quando instancio lista de usuarios por autorizacoes, ele nao salva, pois esta mapeado em usuario*/

    @Test  
    void testaAutorizacao()
    {
        Usuario usuario = usuarioRepo.findById(1L).get();
        assertEquals("role_admin",usuario.getAutorizacoes().iterator().next().getNome());
    }

    @Test
    void testaUsuario()
    {
        Autorizacao aut= autRepo.findById(1L).get();
        assertEquals("victor",aut.getUsuarios().iterator().next().getNome());
    }

    @Test
    void testaBuscarUsuarioNomeContains()
    {
        List<Usuario> usuarios=usuarioRepo.findByNomeContainsIgnoreCase("V");
        assertFalse(usuarios.isEmpty());
    }
    
    @Test
    void testaBuscaUsuarioNome()
    {
        Usuario usuario=usuarioRepo.findByNome("victor");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeQuery()
    {
        Usuario usuario=usuarioRepo.buscaUsuarioporNome("victor");
        assertNotNull(usuario);
    }

     @Test
    void testaBuscaUsuarioNomeSenha()
    {
        Usuario usuario=usuarioRepo.findByNomeAndSenha("victor","boasenha");
        assertNotNull(usuario);
    }

     @Test
    void testaBuscaUsuarioNomeSenhaQuery()
    {
        Usuario usuario=usuarioRepo.busaUsuarioPorNomeESenha("victor","boasenha");
        assertNotNull(usuario);
    }

     @Test
    void testaBuscaUsuarioNomeAutorizacao()
    {
        List<Usuario> usuarios=usuarioRepo.findByAutorizacoesNome("role_admin");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacaoQuery()
    {
        List<Usuario> usuarios=usuarioRepo.buscaPorNomeAutorizacao("role_admin");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaServicoCriaUsuario()
    {
        Usuario usuario= segService.criarUsuario("normal", "senha123", "role_admin");
        assertNotNull(usuario);
    }

}
