package guru.sfg.brewery.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created by jt on 2019-05-31.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerInventoryDto {
    @Null
    private UUID id;
    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate;
    @Null
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime lastModifiedDate;

    private UUID beerId;
    private String upc;
    private Integer quantityOnHand;
}
