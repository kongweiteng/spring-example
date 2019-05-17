package com.example.datagecco;

import com.geccocrawler.gecco.spring.ConsolePipeline;
import com.geccocrawler.gecco.spring.SpringPipelineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigure {

	@Bean
	public SpringPipelineFactory springPipelineFactory() {
		return new SpringPipelineFactory();
	}

	@Bean(name="consolePipeline")
	public ConsolePipeline consolePipeline() {
		return new ConsolePipeline();
	}
}
