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
        repository.findAllByBeerId(uuid).subscribe(
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
        Phaser phaser = new Phaser(2);
        final String beerId = UUID.randomUUID().toString();
        final String id = UUID.randomUUID().toString();
        AtomicReference<BeerInventory> reference = new AtomicReference<>();
        BeerInventory beerInventory = BeerInventory.builder().beerId(beerId).quantityOnHand(30).upc("12345").id(id).build();

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

        System.out.println("0 phase is done!");

        repository.findById(id).subscribe(new Consumer<BeerInventory>() {
            @Override
            public void accept(BeerInventory beerInventory) {
                reference.set(beerInventory);
                phaser.arrive();
                System.out.println("Phaser arrived on success!");
            }
        }, throwable -> {

            System.out.println(throwable.toString());
            phaser.arrive();
            System.out.println("Phaser arrived on error!");

        }, ()-> {
            System.out.println("Phaser arrived on done!");
            phaser.arrive();
        });


        phaser.awaitAdvance(1);
        System.out.println("Phase 1 is done!");
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
        Phaser phaser = new Phaser(2);

        String beerId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        System.out.println("UUID of Inventory is: " + id);

        final AtomicReference<BeerInventory> reference = new AtomicReference<>();

        final BeerInventory beerInventory = BeerInventory.builder().beerId(beerId).quantityOnHand(30).upc("12345").id(id).build();

        repository.save(beerInventory).subscribe(new Consumer<BeerInventory>() {
            @Override
            public void accept(BeerInventory beerInventory) {
                System.out.println("Phaser arrived with success!");
                phaser.arrive();
            }
        }, throwable -> {
            System.out.println("Phaser arrived with error!");
            System.out.println(throwable.getMessage());
            phaser.arrive();
        }, ()->{
            System.out.println("Phaser arrived at done!");
            phaser.arrive();
        });

        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("Phase 0 finished!");

        repository.findById(id).subscribe(beerInventory1 -> {
            reference.set(beerInventory1);
            phaser.arrive();
            System.out.println("Phaser arrived with success!");
        }, throwable -> {
            System.out.println(throwable.toString());
            phaser.arrive();
            System.out.println("Phaser arrived with error!");
        }, ()-> {
            System.out.println("Phaser arrived at done!");
            phaser.arrive();
        });

        phaser.awaitAdvance(phaser.getPhase());

        assertThat(reference.get().getBeerId()).isEqualTo(beerId);
        phaser.arriveAndDeregister();
        repository.delete(BeerInventory.builder().id(id).build())
                .subscribe(Void->{
                    System.out.println("Consumed!");
                },throwable -> {
                    System.out.println(throwable.getMessage() + "*******ERROR!*******");
                    phaser.arrive();
                },()->{
                    phaser.arrive();
                    System.out.println("Done!");
                });

        phaser.awaitAdvance(phaser.getPhase());
        phaser.register();
        reference.set(null);
        repository.findById(id).subscribe(beerInventory1 -> {
            reference.set(beerInventory);
            phaser.arrive();
            System.out.println("Phaser arrived with success!");
        }, throwable -> {
            System.out.println(throwable.toString());
            phaser.arrive();
            System.out.println("Phaser arrived with error!");
        }, ()-> {
            System.out.println("Phaser arrived at done!");
            phaser.arrive();
        });
        assertThat(reference.get()).isNull();
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

        repository.delete(BeerInventory.builder().id("077b7c3c-d9e1-4d54-95f7-da9483cb6a09").build()).subscribe(positive,negative,()-> {System.out.println("Done!"); phaser.arrive();});
        phaser.awaitAdvance(0);
    }


}