package ru.practicum.stats.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {
        "ru/practicum/stats/client"
})
@Configuration
public class StatsClientConfig {
}
