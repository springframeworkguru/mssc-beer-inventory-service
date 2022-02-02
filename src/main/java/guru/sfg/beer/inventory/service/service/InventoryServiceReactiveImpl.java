package guru.sfg.beer.inventory.service.service;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryReactiveRepository;
import guru.sfg.beer.inventory.service.repositories.ReactiveRepository;
import guru.sfg.beer.inventory.service.web.mappers.BeerInventoryMapper;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceReactiveImpl implements InventoryReactiveService {

    private final BeerInventoryMapper beerInventoryMapper;
    private final BeerInventoryReactiveRepository beerInventoryRepository;

    @Override
    public Flux<BeerInventoryDto> findAllInventoryRecordsByBeerId(String beerId) {
        return beerInventoryRepository
                .findAllByBeerId(beerId)
                .switchIfEmpty(Flux.empty())
                .log("Found beerInventories: ")
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto);
    }

    @Transactional
    @Override
    public Mono<Void> newInventoryRecord(Mono<BeerDto> beerDto) {
        return beerDto
                .map(bdto -> BeerInventory.builder().beerId(bdto.getId().toString()).quantityOnHand(bdto.getQuantityOnHand()).upc(bdto.getUpc()).id(getIdAsString()).build())
                .flatMap(beerInventoryRepository::save).then(Mono.empty());
    }

    private String getIdAsString(){
        return UUID.randomUUID().toString();
    }



//    @Override
//    public Mono<Void> newInventoryRecord(Mono<BeerDto> beerDtoMono) {
//        return beerDtoMono.map(beerDto -> BeerInventory.builder().beerId(beerDto.getId()).upc(beerDto.getUpc()).quantityOnHand(beerDto.getQuantityOnHand()).build())
//                .log("Saving entity: ")
//                .flatMap(entity -> {
//                            return beerInventoryRepository.save(entity).then().onErrorResume(new Function<Throwable, Mono<? extends Void>>() {
//                                @Override
//                                public Mono<? extends Void> apply(Throwable throwable) {
//                                    return Mono.error(new RuntimeException("Error happened during inventory saving!"));
//                                }
//                            });
//                        });
//    }
}
