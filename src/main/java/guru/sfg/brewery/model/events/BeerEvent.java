package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {
    static final long serialVersionUID = 6389927906783071796L;
    private BeerDto beerDto;
}
