package guru.sfg.beer.inventory.service.repositories;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.PreparedOperation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class BeerInventoryReactiveRepositoryImpl implements BeerInventoryReactiveRepository {

    private final R2dbcEntityTemplate template;

    @Override
    public Flux<BeerInventory> findAllByBeerId(String id) {
        return template.select(BeerInventory.class)
                .from("beer_inventory")
                .matching(query(where("beer_id").is(id.toString())))
                .all();
    }

    @Transactional(propagation = Propagation.NESTED)
    @Override
    public Mono<BeerInventory> save(BeerInventory save) {
        return template.insert(BeerInventory.class)
                .using(save);
    }

    @Override
    public Flux<BeerInventory> findAllByUpc(String upc) {
        return template.select(BeerInventory.class)
                .from("beer_inventory")
                .matching(query(where("upc").is(upc)))
                .all();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Mono<Void> delete(BeerInventory delete) {
        return template.getDatabaseClient()
                .sql("DELETE FROM `beer_inventory` WHERE `id`=:id")
                .bind("id",delete.getId())
                .then();
    }
    @Transactional(propagation = Propagation.NESTED)
    @Override
    public Mono<BeerInventory> findById(String id) {
        return template.select(BeerInventory.class)
                .from("beer_inventory")
                .matching(query(where("id").is(id.toString())))
                .first();
    }
}
