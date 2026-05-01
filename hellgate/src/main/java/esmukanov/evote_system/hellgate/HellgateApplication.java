package esmukanov.evote_system.hellgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "esmukanov.evote_system")
@EnableJpaRepositories(basePackages = "esmukanov.evote_system")
@EntityScan(basePackages = "esmukanov.evote_system")
public class HellgateApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellgateApplication.class, args);
    }
}
