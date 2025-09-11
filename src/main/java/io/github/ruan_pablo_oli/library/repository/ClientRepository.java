package io.github.ruan_pablo_oli.library.repository;

import io.github.ruan_pablo_oli.library.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository  extends JpaRepository<Client, UUID> {

    Client findByClientId(String clientId);
}
