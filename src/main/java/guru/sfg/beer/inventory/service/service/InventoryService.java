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
}
