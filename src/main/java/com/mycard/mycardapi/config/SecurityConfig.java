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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // MUDANÇA PRINCIPAL

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/usuario/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/atletas/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/lutas/**").hasRole("ADMIN")
                .antMatchers("/api/v1/organizacoesArbitragem/**").hasRole("ADMIN")
                .antMatchers("/api/v1/metodosVitoria/**").hasRole("ADMIN")
                .antMatchers("/api/v1/equipes/**").hasRole("ADMIN")
                .antMatchers("/api/v1/eventos/**").hasRole("ADMIN")
                .antMatchers("/api/v1/funcionarios/**").hasRole("ADMIN")
                .antMatchers("/api/v1/modalidades/**").hasRole("ADMIN")
                .antMatchers("/api/v1/arbitros/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/usuarios/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

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