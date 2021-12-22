package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent{
    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
