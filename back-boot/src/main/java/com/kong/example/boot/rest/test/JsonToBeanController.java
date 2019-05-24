package com.kong.example.boot.rest.test;

import com.kong.example.boot.util.RespEntityUtil;
import com.sun.codemodel.JCodeModel;
import io.swagger.annotations.ApiOperation;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

@RestController
@RequestMapping("jsonToBean")
public class JsonToBeanController {

    @ApiOperation("json to bean}")
    @PostMapping("/jsonToBean")
    public RespEntityUtil jsonToBean() {
        JCodeModel codeModel = new JCodeModel();
        URL resource = Example.class.getResource("/json/test.json");
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, "ClassName", "com.kong.example.bean", resource);

        try {
            codeModel.build(Files.createTempDirectory("required").toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return RespEntityUtil.ok("ok");
    }

}
