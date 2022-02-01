package guru.sfg.beer.inventory.service.web.controllers.reactive;

import guru.sfg.beer.inventory.service.service.InventoryReactiveService;
import guru.sfg.brewery.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeerInventoryHandler {

    private final InventoryReactiveService inventoryService;

    public Mono<ServerResponse> listBeersById(ServerRequest request) {
        return ServerResponse.ok()
                .bodyValue(inventoryService.findAllInventoryRecordsByBeerId(UUID.fromString(request.pathVariable("beerId"))))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
