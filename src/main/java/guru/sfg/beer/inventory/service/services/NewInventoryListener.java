package guru.sfg.beer.inventory.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.brewery.beerservice.domain.Beer;
import guru.sfg.common.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class NewInventoryListener {

   private  final  RabbitTemplate rabbitTemplate;
//
private final BeerInventoryRepository beerInventoryRepository;

//    @Bean // Serialize message content to json using TextMessage
//
//    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        converter.setObjectMapper(objectMapper);
//        return converter;
//    }
//    public NewInventoryListener(){
//        rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setMessageConverter(jacksonJmsMessageConverter);
//    }
//    @Transactional
//    @Scheduled(fixedRate = 5000)//every 5 seconds
//    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)

    @RabbitListener(queues = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(Beer event){
        System.out.println("listening for inventoryyyy"+event.toString());

//        NewInventoryEvent inventoryEvent= (NewInventoryEvent) rabbitTemplate.receiveAndConvert(JmsConfig.NEW_INVENTORY_QUEUE);
//
//        log.debug("Got Inventory: " + inventoryEvent.toString());
//
beerInventoryRepository.save(BeerInventory.builder()
.beerId(event.getId())
            .upc(event.getUpc())
                .quantityOnHand(event.getQuantityOnHand())
               .build());
    }

}


