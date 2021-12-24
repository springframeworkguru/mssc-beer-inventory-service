package guru.sfg.beer.inventory.service.service.order.listener;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.service.order.AllocationService;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderAllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_REQUEST_QUEUE)
    public void listen(@Payload AllocateOrderRequest request){
        Boolean isAllocated=false;
        Boolean allocationError=false;
        try{
            isAllocated = allocationService.allocateOrder(request.getBeerOrderDto());
        } catch (Exception e) {
            log.error("Allocation error with order id {}", request.getBeerOrderDto().getId());
            allocationError=true;
        }

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, AllocateOrderResponse.builder()
                .isAllocated(isAllocated)
                .allocationError(allocationError)
                .beerOrderDto(request.getBeerOrderDto()).build());
    }
}
