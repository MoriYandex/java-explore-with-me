package config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = {
        "client"
})
@Configuration
public class StatsClientConfig {
}
