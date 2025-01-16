package dev.visie.elections.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Order(0)
@Profile({"local", "dev", "prod", "gavin"})
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);


    @Override
    public void run(String... args) {
        logger.info("Database seeding initiated...");
        logger.info("All seeders executed.");
    }
}
