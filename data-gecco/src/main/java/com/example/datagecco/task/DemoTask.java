package com.example.datagecco.task;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DemoTask {
    @Resource
    protected PipelineFactory springPipelineFactory;

    public void test() {
        HttpGetRequest start = new HttpGetRequest("https://github.com/xtuhcy/gecco");
        start.setCharset("GBK");
        GeccoEngine.create()
                .pipelineFactory(springPipelineFactory)
                .classpath("com.example.datagecco")
                .start(start)
                .interval(2000)
                .loop(false)
                .run();
    }
}
