package pl.sulazula.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "pl.sulazula.demo.entity")
public class Sem5HwApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sem5HwApplication.class, args);
	}

}
