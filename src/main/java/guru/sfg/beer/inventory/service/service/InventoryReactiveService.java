package guru.sfg.beer.inventory.service.service;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerInventoryDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface InventoryReactiveService {
    Flux<BeerInventoryDto> findAllInventoryRecordsByBeerId(String id);
    Mono<Void> newInventoryRecord(Mono<BeerDto> beerDto);
}
