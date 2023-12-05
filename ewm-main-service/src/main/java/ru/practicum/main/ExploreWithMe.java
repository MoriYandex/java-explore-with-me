package ru.practicum.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.practicum.stats.config.StatsClientConfig;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = {StatsClientConfig.class}, basePackageClasses = {ru.practicum.stats.client.StatsClient.class})
public class ExploreWithMe {
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMe.class, args);
    }
}
