package io.github.ruan_pablo_oli.library.service;


import io.github.ruan_pablo_oli.library.model.Client;
import io.github.ruan_pablo_oli.library.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;


    public Client salvar(Client client){
        var senha = client.getClientSecret();
        client.setClientSecret(encoder.encode(senha));
        return clientRepository.save(client);
    }

    public Client obterPorClientId(String clientId){
        return clientRepository.findByClientId(clientId);
    }

}
