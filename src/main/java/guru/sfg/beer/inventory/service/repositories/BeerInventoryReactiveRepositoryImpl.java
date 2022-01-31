package guru.sfg.beer.inventory.service.repositories;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class BeerInventoryReactiveRepositoryImpl implements BeerInventoryReactiveRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Flux<BeerInventory> findAllByBeerId(UUID id) {
        return template.select(BeerInventory.class)
                .from("beer_inventory")
                .matching(query(where("beer_id").is(id.toString())))
                .all();
    }
}
