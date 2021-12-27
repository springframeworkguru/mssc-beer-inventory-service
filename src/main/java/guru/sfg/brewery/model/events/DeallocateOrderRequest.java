package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeallocateOrderRequest {

    static final long serialVersionUID = 6347030634014884197L;
    private BeerOrderDto beerOrderDto;
}
