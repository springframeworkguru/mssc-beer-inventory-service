package guru.sfg.beer.inventory.service.domain.converters;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class BeerInventoryWritingConverter implements Converter<BeerInventory, OutboundRow>  {

    @Override
    public OutboundRow convert(BeerInventory source) {
        OutboundRow row = new OutboundRow();
        row.put("id", Parameter.from(source.getId().toString()));
        row.put("beer_id", Parameter.from(source.getBeerId().toString()));
        row.put("upc", Parameter.from(source.getUpc()));
        row.put("quantity_on_hand", Parameter.from(source.getQuantityOnHand()));
        return row;
    }
}
