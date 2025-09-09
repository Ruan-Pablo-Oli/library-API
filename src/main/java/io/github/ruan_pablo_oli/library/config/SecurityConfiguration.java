package io.github.ruan_pablo_oli.library.config;


import io.github.ruan_pablo_oli.library.security.CustomUserDetailsService;
import io.github.ruan_pablo_oli.library.security.LoginSocialSucessHandler;
import io.github.ruan_pablo_oli.library.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSucessHandler sucessHandler) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
              .formLogin(configurer -> {
                   configurer.loginPage("/login");
               })
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorizer ->{
                    authorizer.requestMatchers("/login/**").permitAll();
                    authorizer.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();
//                    authorizer.requestMatchers( HttpMethod.POST,"/autores/**").hasRole("ADMIN");
//                    authorizer.requestMatchers(HttpMethod.PUT,"/autores/**").hasRole("ADMIN");
//                    authorizer.requestMatchers(HttpMethod.DELETE,"/autores/**").hasRole("ADMIN");
//                    authorizer.requestMatchers(HttpMethod.GET,"/autores/**").hasAnyRole("ADMIN");
//                    authorizer.requestMatchers("/autores/**").hasRole("ADMIN");
//                    authorizer.requestMatchers("/livros/**").hasAnyRole("USER","ADMIN");
                    authorizer.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .successHandler(sucessHandler);
                })
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

 //   @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){

        return new CustomUserDetailsService(usuarioService);
    }
}
