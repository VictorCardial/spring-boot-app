package br.gov.sp.fatec.springbootapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@RestController /*anotacao que permite que a classe seja vista como controller e os metodos podem ser rotas*/
@RequestMapping(value = "/usuario") /*anotacao que serve para endereçar o servico*/
@CrossOrigin
public class UsuarioController {
    
    /*criação das rotas*/
    /*serviço para trazer todos os usuario*/

    @Autowired
    private SegurancaService segurancaService;

    @GetMapping
    public List<Usuario> buscarTodos()
    {
        return segurancaService.buscarTodosUsuarios();
    }


     @GetMapping (value="/{id}")
    public Usuario buscarPorId(@PathVariable("id") Long id)
    {
        return segurancaService.buscarUsuarioPorId(id);
    }

    @GetMapping
    public Usuario buscarPorNome(@RequestParam(value = "nome")  String nome)
    {
        return segurancaService.buscarUsuarioPorNome(nome);
    }
}