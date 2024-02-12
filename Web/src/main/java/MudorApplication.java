import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "Mudor.repository")
@EntityScan("Mudor.entity")
@SpringBootApplication
@ComponentScan(basePackages = "Mudor")
public class MudorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MudorApplication.class, args);
	}

}
