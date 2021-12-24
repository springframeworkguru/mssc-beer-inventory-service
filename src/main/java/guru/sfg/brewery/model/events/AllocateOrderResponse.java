package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllocateOrderResponse {
    private BeerOrderDto beerOrderDto;
    private Boolean isAllocated=false;
    private Boolean allocationError=false;
}
