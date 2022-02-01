package guru.sfg.beer.inventory.service.repositories;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerInventoryReactiveRepositoryImplTest {

    @Autowired
    BeerInventoryReactiveRepository repository;

    @Test
    void findAllByBeerId() {
        Phaser phaser = new Phaser(1);
        String uuid = "a712d914-61ea-4623-8bd0-32c0f6545bfd";
        repository.findAllByBeerId(UUID.fromString(uuid)).subscribe(
                System.out::println,
                throwable -> System.out.println(throwable.getMessage()),
                ()->{
                    System.out.println("Done!");
                    phaser.arrive();
                });

        phaser.awaitAdvance(0);
    }

    @Test
    void save() {
        Phaser phaser = new Phaser(1);
        final UUID beerId = UUID.randomUUID();
        AtomicReference<BeerInventory> reference = new AtomicReference<>();
        BeerInventory beerInventory = BeerInventory.builder().beerId(beerId).quantityOnHand(30).upc("12345").id(UUID.randomUUID()).build();

        repository.save(beerInventory).subscribe(new Consumer<BeerInventory>() {
            @Override
            public void accept(BeerInventory beerInventory) {
                phaser.arrive();
                System.out.println("Phaser arrived on success!");
            }
        }, throwable -> {

            System.out.println(throwable.toString());
            phaser.arrive();
            System.out.println("Phaser arrived on error!");

        }, ()-> {
            phaser.arrive();
            System.out.println("Phaser arrived on done!");
        });

        phaser.awaitAdvance(0);

        System.out.println("First phase is done!");

        repository.findAllByBeerId(beerId).take(1).subscribe(beerInventory1 -> {
                    reference.set(beerInventory1);
                },
                throwable -> { System.out.println(throwable.getMessage() + "\n" + throwable.getCause()); phaser.arrive();},
                ()->{
                    System.out.println("Done!");
                    phaser.arrive();
                });

        phaser.awaitAdvance(1);
        System.out.println("Phase 2 is done!");
        assertThat(reference.get().getBeerId()).isEqualTo(beerId);
    }

    @Test
    void findAllByUpc() {
        Phaser phaser = new Phaser(1);
        String upc = "0083783375213";
        repository.findAllByUpc(upc).subscribe(
                System.out::println,
                throwable -> System.out.println(throwable.getMessage()),
                ()->{
                    System.out.println("Done!");
                    phaser.arrive();
                });

        phaser.awaitAdvance(0);
    }

    @Test
    void delete() {
        Phaser phaser = new Phaser(1);
        UUID beerId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        System.out.println("UUID of Inventory is: " + id);
        final AtomicReference<BeerInventory> reference = new AtomicReference<>();
        final AtomicReference finalReference = new AtomicReference<>();

        BeerInventory beerInventory = BeerInventory.builder().beerId(beerId).quantityOnHand(30).upc("12345").id(id).build();
        repository.save(beerInventory).subscribe(new Consumer<BeerInventory>() {
            @Override
            public void accept(BeerInventory beerInventory) {
                phaser.arrive();
            }
        }, throwable -> {
            System.out.println(throwable.getMessage());
            phaser.arrive();
        });

        phaser.awaitAdvance(0);

        repository.findAllByBeerId(beerId).take(1).subscribe(beerInventory1 -> {
                    reference.set(beerInventory1);
                },
                throwable -> {System.out.println(throwable.getMessage()); phaser.arrive();},
                ()->{
                    System.out.println("Finding is done!");
                    phaser.arrive();
                });
        phaser.awaitAdvance(1);
        assertThat(reference.get().getBeerId()).isEqualTo(beerId);

        Consumer positive = new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println("Consumed!");
            }
        };

        Consumer<Throwable> negative = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                System.out.println(throwable.getMessage());
                phaser.arrive();
            }
        };

        repository.delete(BeerInventory.builder().id(UUID.randomUUID()).build())
                .subscribe(positive,negative,()-> {System.out.println("Done!");});


        phaser.awaitAdvance(1);

        repository.findAllByBeerId(beerId).take(1).subscribe(beerInventory1 -> {
                    finalReference.set(beerInventory1);
                },
                throwable -> {
                System.out.println(throwable.getMessage());
                phaser.arrive();
                },
                ()->{
                    System.out.println("Finding is done!");
                    phaser.arrive();
                });
        phaser.awaitAdvance(2);
        assertThat(finalReference.get()).isNull();
    }

    @Test
    void deleteTest() {
        Phaser phaser = new Phaser(1);
        Consumer positive = System.out::println;
        Consumer<Throwable> negative = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                System.out.println(throwable.getMessage());
                phaser.arrive();
            }
        };

        repository.delete(BeerInventory.builder().id(UUID.fromString("0161e450-4c92-41d9-a669-aea6b9a0d87d")).build()).subscribe(positive,negative,()-> {System.out.println("Done!"); phaser.arrive();});
        phaser.awaitAdvance(0);
    }
}