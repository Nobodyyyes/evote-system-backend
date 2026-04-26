package esmukanov.evote_system.hellgate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "esmukanov.evote_system")
public class HellgateApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellgateApplication.class, args);
    }
}
