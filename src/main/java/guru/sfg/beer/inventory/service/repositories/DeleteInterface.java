package guru.sfg.beer.inventory.service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

public interface DeleteInterface {

    @Query("DELETE FROM `beer_inventory` WHERE `id`= :inventoryId")
    Mono<Void> deleteFromInventory(@Param("inventoryId") String inventoryId);
}
