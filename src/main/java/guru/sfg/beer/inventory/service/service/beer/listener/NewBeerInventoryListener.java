package guru.sfg.beer.inventory.service.service.beer.listener;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.service.InventoryReactiveService;
import guru.sfg.beer.inventory.service.service.InventoryService;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewBeerInventoryListener {

    private final InventoryReactiveService inventoryService;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(@Payload NewInventoryEvent event){
        log.debug("New inventory event!");
        inventoryService.newInventoryRecord(Mono.just(event.getBeerDto()));
    }
    //for instance how to work with Message interface
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
