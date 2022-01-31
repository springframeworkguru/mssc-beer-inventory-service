package guru.sfg.beer.inventory.service.web.controllers.reactive;

import guru.sfg.beer.inventory.service.service.InventoryReactiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Profile("reactive")
public class BeerInventoryHandler {

    private final InventoryReactiveService inventoryService;

    public Mono<ServerResponse> listBeersById(ServerRequest request) {
        return ServerResponse.ok()
                .body(BodyInserters.fromValue(inventoryService.findAllInventoryRecordsByBeerId(UUID.fromString(request.pathVariable("beerId")))));
    }
}
