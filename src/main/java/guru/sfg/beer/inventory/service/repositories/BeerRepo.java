package guru.sfg.beer.inventory.service.repositories;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BeerRepo extends ReactiveCrudRepository<BeerInventory, UUID> {

    @Query("DELETE FROM `beer_inventory` WHERE `id`= :inventoryId")
    Mono<Void> deleteFromInventory(@Param("inventoryId") String inventoryId);

}
