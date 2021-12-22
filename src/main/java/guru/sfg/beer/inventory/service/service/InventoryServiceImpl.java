package guru.sfg.beer.inventory.service.service;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.beer.inventory.service.web.mappers.BeerInventoryMapper;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final BeerInventoryMapper beerInventoryMapper;
    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public List<BeerInventoryDto> findAllInventoryRecordsByBeerId(UUID beerId) {
        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public void newInventoryRecord(BeerDto beerDto) {
//        BeerInventoryDto dto = BeerInventoryDto.builder().beerId(beerDto.getId()).quantityOnHand(beerDto.getQuantityOnHand()).upc(beerDto.getUpc()).build();
        beerInventoryRepository.save(BeerInventory.builder().beerId(beerDto.getId()).upc(beerDto.getUpc()).quantityOnHand(beerDto.getQuantityOnHand()).build());
        log.debug("New beer inventory is successfully saved!");
    }
}
