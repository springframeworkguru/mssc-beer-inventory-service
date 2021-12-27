package guru.sfg.beer.inventory.service.service.order;

import guru.sfg.brewery.model.BeerOrderDto;

public interface AllocationService {

    Boolean allocateOrder(BeerOrderDto dto);
    void deallocateOrder(BeerOrderDto dto);
}
