package guru.sfg.beer.inventory.service.web.mappers;

import com.netflix.discovery.converters.Auto;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.brewery.model.BeerInventoryDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class BeerInventoryDecorator implements BeerInventoryMapper {

    private DateMapper dateMapper;

    @Autowired
    public void setDateMapper(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }

    @Override
    public BeerInventory beerInventoryDtoToBeerInventory(BeerInventoryDto dto) {
        return BeerInventory.builder()
                .id(dto.getId().toString())
                .beerId(dto.getBeerId().toString())
                .quantityOnHand(dto.getQuantityOnHand())
                .createdDate(dateMapper.asTimestamp(dto.getCreatedDate()))
                .lastModifiedDate(dateMapper.asTimestamp(dto.getLastModifiedDate()))
                .upc(dto.getUpc()).build();
    }

    @Override
    public BeerInventoryDto beerInventoryToBeerInventoryDto(BeerInventory inv) {
        return BeerInventoryDto.builder()
                .id(UUID.fromString(inv.getId()))
                .beerId(UUID.fromString(inv.getBeerId()))
                .quantityOnHand(inv.getQuantityOnHand())
                .upc(inv.getUpc())
                .createdDate(dateMapper.asOffsetDateTime(inv.getCreatedDate()))
                .lastModifiedDate(dateMapper.asOffsetDateTime(inv.getLastModifiedDate()))
                .build();
    }
}
