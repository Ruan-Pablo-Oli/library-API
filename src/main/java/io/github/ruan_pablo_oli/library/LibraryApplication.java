package io.github.ruan_pablo_oli.library;

import io.github.ruan_pablo_oli.library.model.Autor;
import io.github.ruan_pablo_oli.library.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(LibraryApplication.class, args);
		AutorRepository autorRepository = context.getBean(AutorRepository.class);

		salvarRegistro(autorRepository);
	}


	public static void salvarRegistro(AutorRepository autorRepository){
		Autor newAutor = new Autor();
		newAutor.setNome("José");
		newAutor.setDataNascimento(LocalDate.of(1950,1,31));
		newAutor.setNacionalidade("Brasileira");

		var autorSalvo = autorRepository.save(newAutor);

		System.out.print("Autor salvo:  " + autorSalvo);
	}


}
