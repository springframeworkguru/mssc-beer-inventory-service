package guru.sfg.beer.inventory.service.listeners;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.services.AllocationService;
import guru.sfg.model.events.AllocateBeerOrderRequest;
import guru.sfg.model.events.AllocateBeerOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateBeerOrderRequest request){
        AllocateBeerOrderResult.AllocateBeerOrderResultBuilder builder = AllocateBeerOrderResult.builder();
        builder.beerOrderId(request.getBeerOrder().getId());

        try {
            Boolean allocationResult = allocationService.allocateOrder(request.getBeerOrder());

            if (allocationResult){
                builder.allocated(true).pendingInventory(false);
            } else {
                builder.allocated(false).pendingInventory(true);
            }

        } catch (Exception e) {
            //some error occured
            builder.allocated(false).pendingInventory(false);
            log.error("Allocation attempt failed for order id " + request.getBeerOrder().getId(), e);
        }

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESULT_QUEUE, builder.build());
    }
}
