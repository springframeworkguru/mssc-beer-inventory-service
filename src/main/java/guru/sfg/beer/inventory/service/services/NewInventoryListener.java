package guru.sfg.beer.inventory.service.services;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.common.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class NewInventoryListener {

    private final RabbitTemplate rabbitTemplate;

    private final BeerInventoryRepository beerInventoryRepository;

    @Transactional
    @Scheduled(fixedRate = 5000)//every 5 seconds
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(){
        System.out.println("listening for inventoryyyy");

        NewInventoryEvent inventoryEvent= (NewInventoryEvent) rabbitTemplate.receiveAndConvert(JmsConfig.NEW_INVENTORY_QUEUE);

        log.debug("Got Inventory: " + inventoryEvent.toString());

        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(inventoryEvent.getBeerDto().getId())
                .upc(inventoryEvent.getBeerDto().getUpc())
                .quantityOnHand(inventoryEvent.getBeerDto().getQuantityOnHand())
                .build());
    }

}


