package guru.sfg.model.events;

import guru.sfg.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent {

    private BeerDto beerDto;
}
