package guru.sfg.beer.inventory.service.config;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@Configuration
@Profile("reactive")
@EnableR2dbcRepositories(basePackages = "guru.sfg.beer.inventory.service.repositories")
public class ReactiveDataSourceConfig extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                "r2dbc:mysql://beer_inventory_service:password@database-1.ck6kohrdxhty.us-east-2.rds.amazonaws.com:3306/beerinventoryservice?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
    }

    // Creating a Mono using Project Reactor
    @Bean
    public Mono<Connection> connectionMono(){
        return Mono.from(connectionFactory().create());
    }

    @Bean
    public R2dbcEntityOperations mysqlR2dbcEntityOperations(ConnectionFactory connectionFactory) {
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        return new R2dbcEntityTemplate(databaseClient, MySqlDialect.INSTANCE);
    }
}
