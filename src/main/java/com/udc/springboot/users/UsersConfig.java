package com.udc.springboot.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UsersConfig {

    @Bean
    CommandLineRunner commandLineRunner(UsersRepository repository) {
        return args -> {
            Users Cyrus = new Users(

                    "Cyrus Burt",
                    "1-456-842-7528"
            );

            Users Ethan = new Users(

                    "Ethan Simpson",
                    "(493) 778-2015"
            );

            repository.saveAll(
                    List.of(Cyrus,
                            Ethan)
            );

        };
    }
}
