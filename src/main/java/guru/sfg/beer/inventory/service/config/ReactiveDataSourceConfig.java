package guru.sfg.beer.inventory.service.config;

import dev.miku.r2dbc.mysql.MySqlConnectionFactoryProvider;
import io.r2dbc.spi.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
public class ReactiveDataSourceConfig extends AbstractR2dbcConfiguration {

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, "mysql")
                .option(HOST, "database-1.ck6kohrdxhty.us-east-2.rds.amazonaws.com")
                .option(USER, "beer_inventory_service")
                .option(PORT, 3306)  // optional, default 3306
                .option(PASSWORD, "password") // optional, default null, null means has no password
                .option(DATABASE, "beerinventoryservice") // optional, default null, null means not specifying the database
                .option(Option.valueOf("useUnicode"), "true")
                .option(Option.valueOf("characterEncoding"), "UTF-8")
                .option(Option.valueOf("serverTimezone"), "UTC")
                .build();
        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
        return connectionFactory;
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
