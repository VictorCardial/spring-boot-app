package br.gov.sp.fatec.springbootapp.security;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    //aqui estão os metodos que lidam com jwt

    private static final String KEY ="spring.jwt.sec"; //chave

    //função para geração do token
    public static String generateToken(Authentication usuario) throws JsonProcessingException
    {
        ObjectMapper mapper= new ObjectMapper();
        Login usuarioSemSenha = new Login();
        usuarioSemSenha.setUsername(usuario.getName());

        if(!usuario.getAuthorities().isEmpty()) //se não é vazio, então pega o nome da autorização que vem junto
        {
            usuarioSemSenha.setAutorizacao(usuario.getAuthorities().iterator().next().getAuthority());
        }
        //gera-se um json com o usuario, usando o mapper
        String usuarioJson= mapper.writeValueAsString(usuarioSemSenha);
        Date agora= new Date();
        Long hora =1000L * 60L * 60L; //uma hora em miliseg

        //cria o token com a biblioteca jwts
        return Jwts.builder().claim("userDetails",usuarioJson)
        .setIssuer("br.gov.sp.fatec") //quem está gerando? fatec
        .setSubject(usuario.getName()) //para quem se destina? usuario
        .setExpiration(new Date(agora.getTime()+ hora)) //quando espira? data atual +1 hora
        .signWith(SignatureAlgorithm.HS512,KEY) //assinatura com hs512+chave
        .compact();
        //gerada a string token
    }

    //metodo de verificação parse

    public static User parseToken(String token) throws JsonParseException, JsonMappingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser()
        .setSigningKey(KEY) //setando a chave
        .parseClaimsJws(token)  //parse do token- não precisou validar o token,  assinatura, validade...
        .getBody()
        .get("userDetails",String.class); //pegando o corpo do token
    
        Login usuario =mapper.readValue(credentialsJson,Login.class); //o corpo será remontado em usuario
        
        return (User) User.builder().username(usuario.getUsername()) //retorna o usuario
        .password("secret")
        .authorities(usuario.getAutorizacao())
        .build();
    } 

}