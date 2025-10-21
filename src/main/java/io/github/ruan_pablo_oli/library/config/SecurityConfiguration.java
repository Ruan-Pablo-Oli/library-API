package io.github.ruan_pablo_oli.library.config;


import io.github.ruan_pablo_oli.library.security.CustomUserDetailsService;
import io.github.ruan_pablo_oli.library.security.JwtCustomAuthenticationFilter;
import io.github.ruan_pablo_oli.library.security.LoginSocialSucessHandler;
import io.github.ruan_pablo_oli.library.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSucessHandler sucessHandler, JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception{
        return http.csrf(AbstractHttpConfigurer::disable)
              .formLogin(configurer -> {
                   configurer.loginPage("/login");
               })
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
                .oauth2ResourceServer(oauath2Rs -> oauath2Rs.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
              "/v2/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                         "/webjars/**"
                    );

    }


    // Configura  o prefixo role
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }


    // Configura no JTW o prefixo scope
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
}
