package ru.practicum.stats.client.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {
        "ru/practicum/stats/client/client"
})
@Configuration
public class StatsClientConfig {
}
