import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Questa classe rappresenta l'applicazione principale Mudor.
 * Configura e avvia l'applicazione Spring Boot per gestire il sistema Mudor.
 */
@EnableJpaRepositories(basePackages = "Mudor.repository")
@EntityScan("Mudor.entity")
@SpringBootApplication
@ComponentScan(basePackages = "Mudor")
public class MudorApplication {

	/**
	 * Metodo principale che avvia l'applicazione Mudor.
	 *
	 * @param args gli argomenti della riga di comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(MudorApplication.class, args);
	}

}
