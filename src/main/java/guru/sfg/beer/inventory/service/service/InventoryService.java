package guru.sfg.beer.inventory.service.service;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerInventoryDto;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<BeerInventoryDto> findAllInventoryRecordsByBeerId(UUID id);
    void newInventoryRecord(BeerDto beerDto);
}
