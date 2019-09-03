package com.example.kafka.kafkademo;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

public class NihaoTest {
    RestTemplate restTemplate = new RestTemplate();
    private String url = "http://localhost:8081/deliverGroup/getParkBySystemCode?systemCode=PARK08_EMS01";
    public static int user = 10000;
    private static CountDownLatch cdl = new CountDownLatch(user);
    @Test
    public void testInvoker() throws InterruptedException {
        for(int i=0;i<user;i++){
            new Thread(new LoginRequest()).start();
            cdl.countDown();
        }
        Thread.currentThread().sleep(200000);
    }
    public class LoginRequest implements Runnable{
        @Override
        public void run() {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            LoginVO loginVO = new LoginVO();
//            loginVO.setUserName("admin10649");
//            loginVO.setPassword("abc123");
//            loginVO.setType("2");

            HttpHeaders headers = new HttpHeaders();
            headers.add("ticket", "123456");
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity httpEntity = new HttpEntity(headers);


            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class, httpEntity);

            System.out.println(forEntity.getBody());
        }
    }
}
