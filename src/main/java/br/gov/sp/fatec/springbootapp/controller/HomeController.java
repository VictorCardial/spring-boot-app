package br.gov.sp.fatec.springbootapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin /*Com ela todos os metodos dentro irão permitir acesso externo, posso especificar quais sites podem me
acessar, colocando CrossOrigin(origins) com uma string, se não colocar nada, ele habilita acesso de qualquer aplicação*/
public class HomeController {
    
    @GetMapping
    public String welcome()
    {
        return "Hello World !";
    }

}