package guru.sfg.beer.inventory.service.web.controllers.reactive;

import guru.sfg.beer.inventory.service.service.InventoryReactiveService;
import guru.sfg.brewery.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Slf4j
public class BeerInventoryHandler {

    private final InventoryReactiveService inventoryService;

    public Mono<ServerResponse> listBeersById(ServerRequest request) {
        log.debug("Received a request for beerId {}", request.pathVariable("beerId"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(inventoryService.findAllInventoryRecordsByBeerId(request.pathVariable("beerId")), BeerInventoryDto.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
