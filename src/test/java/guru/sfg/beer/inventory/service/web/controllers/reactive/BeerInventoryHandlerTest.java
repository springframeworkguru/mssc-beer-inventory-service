package guru.sfg.beer.inventory.service.web.controllers.reactive;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.Phaser;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BeerInventoryHandlerTest {

    WebClient client;

    @BeforeEach
    void setup(){
        client = WebClient.builder()
                .baseUrl("http://localhost:8082")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Test
    void listBeersById() {
        String uuid = "a712d914-61ea-4623-8bd0-32c0f6545bfd";
        Phaser phaser = new Phaser(1);

        Consumer<BeerInventory> consumer = System.out::println;
        Consumer<Throwable> throwableConsumer = t -> System.out.println(t.getMessage());

        client.get().uri(uriBuilder -> uriBuilder.path("api/v2/beer/{beerId}/inventory").build(uuid))
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(BeerInventory.class)
                .subscribe(consumer,throwableConsumer,()-> { System.out.println("Done!"); phaser.arrive();});

        phaser.awaitAdvance(0);
    }
}