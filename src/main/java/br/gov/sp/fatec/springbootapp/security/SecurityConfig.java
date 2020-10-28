package br.gov.sp.fatec.springbootapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    /*@Autowired
    private userDetailsService userDetailsService;*/
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable().httpBasic().and()
        // isso desabilita a sessao de criação no Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService);
    }*/

    //passwordEncoder - encriptando a senha
    @Bean
    public PasswordEncoder PasswordEncoderBean()
    {
        return new BCryptPasswordEncoder();
    }


}