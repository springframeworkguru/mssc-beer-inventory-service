package guru.sfg.beer.inventory.service.domain.converters;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

import java.util.UUID;

@ReadingConverter
public class BeerInventoryReadingConverter implements Converter<Row, BeerInventory>  {

    @Override
    public BeerInventory convert(Row source) {
        return BeerInventory.builder()
                .id(UUID.fromString(source.get("id", String.class)))
                .beerId(UUID.fromString(source.get("beer_id", String.class)))
                .quantityOnHand(source.get("quantity_on_hand", Integer.class))
                .upc(source.get("upc", String.class))
                .build();
    }

}
