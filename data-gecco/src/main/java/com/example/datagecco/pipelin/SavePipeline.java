package com.example.datagecco.pipelin;

import com.alibaba.fastjson.JSON;
import com.example.datagecco.MyGithub;
import com.geccocrawler.gecco.pipeline.Pipeline;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @Description :
 * @Author :WeiHui.Zhang
 * @Data : 2016/3/30 10:51
 * @Version:1.0.0
 */
@Service
@Slf4j
public class SavePipeline implements Pipeline<MyGithub> {


    @Override
    public void process(MyGithub bean) {
        log.info(JSON.toJSONString(bean));
    }
}
