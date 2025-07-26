package io.github.ruan_pablo_oli.library.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="autor", schema = "public")
@Getter
@Setter
@ToString(exclude = {"livros"})
@EntityListeners(AuditingEntityListener.class) // Class vai ficar escutando toda vez que for alterada na entidade e jogar alteraçoes nas clases com anottaiton @CreateData e @LastModifiedDate1

public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column( name = "nome", nullable = false, length = 100)
    private String nome;

    @Column( name = "data_nascimento",nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade",nullable = false,length = 50)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@Transient // ignora a propriedade
    private List<Livro> livros;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;



}
