package guru.sfg.beer.inventory.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;
    private final ObjectMapper mapper;

    @Transactional
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(Message message) throws JMSException, IOException {
        String beerDto = message.getBody(String.class);
        NewInventoryEvent event = mapper.readValue(beerDto, NewInventoryEvent.class);
        log.debug("Got Inventory: " + event.toString());
        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(event.getBeerDto().getId())
                .upc(event.getBeerDto().getUpc())
                .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                .build());
    }
}
