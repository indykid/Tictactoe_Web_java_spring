package kg.jarkyn.tictactoe_web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Config {

    @Bean
    public WebUI webUI() {
        return new WebUI();
    }
}
