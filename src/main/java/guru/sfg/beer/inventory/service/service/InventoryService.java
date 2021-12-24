package guru.sfg.beer.inventory.service.service;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerInventoryDto;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<BeerInventoryDto> findAllInventoryRecordsByBeerId(UUID id);
    void newInventoryRecord(BeerDto beerDto);

    @Service
    @RequiredArgsConstructor
    @Slf4j
    class NewBeerInventoryListener {

    //    private final ObjectMapper objectMapper;
        private final InventoryService inventoryService;

        @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
        public void listen(@Payload NewInventoryEvent event){
            log.debug("New inventory event!");
            inventoryService.newInventoryRecord(event.getBeerDto());
        }
    //    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    //    public void listen(Message message){
    //        try {
    //            NewInventoryEvent newInventoryEvent = objectMapper.readValue(message.getBody(String.class), NewInventoryEvent.class);
    //            BeerDto beerDto = newInventoryEvent.getBeerDto();
    //            inventoryService.newInventoryRecord(beerDto);
    //        } catch (JsonProcessingException | JMSException e) {
    //            try {
    //                message.acknowledge();
    //            } catch (JMSException ex) {
    //                ex.printStackTrace();
    //            }
    //            e.printStackTrace();
    //        }
    //    }
    }
}
