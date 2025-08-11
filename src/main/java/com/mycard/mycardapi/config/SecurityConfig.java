package com.mycard.mycardapi.config;

import com.mycard.mycardapi.security.JwtAuthFilter;
import com.mycard.mycardapi.security.JwtService;
import com.mycard.mycardapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    // 1. INJEÇÃO DO PASSWORD ENCODER
    // Injeta o bean PasswordEncoder que já existe no contexto da aplicação.
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    // Completa a configuração do gerenciador de autenticação.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder);
    }

    // Configura as regras de autorização para os endpoints HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/usuario/**").permitAll()
                .antMatchers("/api/v1/atletas/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/lutas/**").hasRole("ADMIN")
                .antMatchers("/api/v1/organizacoesArbitragem/**").permitAll()
                .antMatchers("/api/v1/metodosVitoria/**").hasRole("admin")
                .antMatchers("/api/v1/equipes/**").permitAll()
                .antMatchers("/api/v1/eventos/**").permitAll()
                .antMatchers("/api/v1/funcionarios/**").permitAll()
                .antMatchers("/api/v1/modalidades/**").permitAll()
                .antMatchers("/api/v1/arbitros/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // Configura recursos que devem ser ignorados pela segurança (ex: Swagger UI)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}