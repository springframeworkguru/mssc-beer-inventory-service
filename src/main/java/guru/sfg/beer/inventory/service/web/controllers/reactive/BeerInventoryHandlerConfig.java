package guru.sfg.beer.inventory.service.web.controllers.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BeerInventoryHandlerConfig {

    @Bean
    RouterFunction<ServerResponse> beerinventoryRouter(BeerInventoryHandler handler){
        return route().GET("api/v2/beer/{beerId}/inventory",accept(APPLICATION_NDJSON), handler::listBeersById)
                .build();
    }
}
