package com.example.datagecco;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.spring.SpringGeccoEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class DataGeccoApplication {

    //    @Bean
    public SpringGeccoEngine initGecco() {
        return new SpringGeccoEngine() {
            @Override
            public void init() {
                GeccoEngine.create()
                        .pipelineFactory(springPipelineFactory)
                        .classpath("com.example.datagecco")
                        .start("https://github.com/xtuhcy/gecco")
                        .interval(3000)
                        .loop(true)
                        .start();
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DataGeccoApplication.class, args);
    }

}
