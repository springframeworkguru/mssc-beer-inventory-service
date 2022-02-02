/*
 *  Copyright 2019 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package guru.sfg.beer.inventory.service.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

/**
 * Created by jt on 2019-01-26.
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BeerInventory{

    @Builder
    public BeerInventory(String id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String beerId, String upc, Integer quantityOnHand) {
        this.id = id;
        this.version = version;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.beerId = beerId;
        this.upc = upc;
        this.quantityOnHand = quantityOnHand;
    }

        @Id
        private String id;
        private Long version;
        private Timestamp createdDate;
        private Timestamp lastModifiedDate;
        private String beerId;
        private String upc;
        private Integer quantityOnHand = 0;


}
