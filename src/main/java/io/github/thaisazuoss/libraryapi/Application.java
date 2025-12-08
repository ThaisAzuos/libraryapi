package io.github.thaisazuoss.libraryapi;

import io.github.thaisazuoss.libraryapi.model.Autor;
import io.github.thaisazuoss.libraryapi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        var context = SpringApplication.run(Application.class, args);
//        AutorRepository repository = context.getBean(AutorRepository.class);
//        exemploSalvarAutor(repository);
	}

//    public static void exemploSalvarAutor(AutorRepository autorRepository){
//        Autor autor = new Autor();
//        autor.setNome("Monteiro Lobato");
//        autor.setNacionalidade("Brasileira");
//        autor.setDataNascimento(LocalDate.of(1882, 4, 18));
//        var autorSalvo = autorRepository.save(autor);
//        System.out.println(autorSalvo);
//
//
//    }
}
