package guru.sfg.beer.inventory.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;

@SpringBootApplication
public class BeerInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerInventoryApplication.class, args);
    }

}
