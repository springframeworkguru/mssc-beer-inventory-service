package guru.sfg.beer.inventory.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations= "classpath:test.properties")
public class BeerInventoryApplicationTests {

    @Test
    public void contextLoads() {
    }

}
