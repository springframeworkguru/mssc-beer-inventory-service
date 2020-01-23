package guru.sfg.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateBeerOrderResult {
    private UUID beerOrderId;
    private Boolean allocated;
    private Boolean pendingInventory = false;
}
