package guru.sfg.beer.inventory.service.repositories;

import guru.sfg.beer.inventory.service.domain.BeerInventory;

import java.util.UUID;

public interface BeerInventoryReactiveRepository extends ReactiveRepository<BeerInventory, String> {
}
