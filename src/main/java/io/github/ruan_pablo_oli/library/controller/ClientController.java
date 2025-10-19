package io.github.ruan_pablo_oli.library.controller;


import io.github.ruan_pablo_oli.library.model.Client;
import io.github.ruan_pablo_oli.library.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clientes")
public class ClientController {

    private final ClientService clientService;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody Client client){
        clientService.salvar(client);
    }

}
