package com.example.datagecco.task;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.spring.SpringGeccoEngine;
import org.springframework.stereotype.Component;

@Component
public class DemoTask {
    public SpringGeccoEngine test() {
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
}
