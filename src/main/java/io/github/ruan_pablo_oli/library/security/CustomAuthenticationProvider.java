package io.github.ruan_pablo_oli.library.security;

import io.github.ruan_pablo_oli.library.model.Usuario;
import io.github.ruan_pablo_oli.library.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;


    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);

        if(usuarioEncontrado == null){
            throw getUsernameNotFoundException();
        }

        String senhaCriptografada = usuarioEncontrado.getSenha();
        boolean senhaBate = encoder.matches(senhaDigitada,senhaCriptografada);

        if(senhaBate){
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw getUsernameNotFoundException();




    }

    private  UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("Usuário e/ou senha incorreto!");
    }

    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
