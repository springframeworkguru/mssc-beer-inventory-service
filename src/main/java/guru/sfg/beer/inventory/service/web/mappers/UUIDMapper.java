package guru.sfg.beer.inventory.service.web.mappers;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDMapper {

    public String UUIDToString(UUID source){
        return source.toString();
    }

    public UUID stringToUUID(String source) {
        return UUID.fromString(source);
    }

}
