package guru.sfg.beer.inventory.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations= "classpath:application.properties")
public class BeerInventoryApplicationTests {

    @Test
    public void contextLoads() {
    }

}
