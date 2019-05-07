package com.kong.example.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 *
 *
 *  * 需要根据几个值来决定
 *             - tasks ：每秒的任务数，假设为500~1000
 *             - taskcost：每个任务花费时间，假设为0.1s
 *             - responsetime：系统允许容忍的最大响应时间，假设为1s
 *         * 做几个计算
 *             - corePoolSize = 每秒需要多少个线程处理？
 *                 * threadcount = tasks/(1/taskcost) =tasks*taskcout =  (500~1000)*0.1 = 50~100 个线程。corePoolSize设置应该大于50
 *                 * 根据8020原则，如果80%的每秒任务数小于800，那么corePoolSize设置为80即可
 *             - queueCapacity = (coreSizePool/taskcost)*responsetime
 *                 * 计算可得 queueCapacity = 80/0.1*1 = 80。意思是队列里的线程可以等待1s，超过了的需要新开线程来执行
 *                 * 切记不能设置为Integer.MAX_VALUE，这样队列会很大，线程数只会保持在corePoolSize大小，当任务陡增时，不能新开线程来执行，响应时间会随之陡增。
 *             - maxPoolSize = (max(tasks)- queueCapacity)/(1/taskcost)
 *                 * 计算可得 maxPoolSize = (1000-80)/10 = 92
 *                 * （最大任务数-队列容量）/每个线程每秒处理能力 = 最大线程数
 *             - rejectedExecutionHandler：根据具体情况来决定，任务不重要可丢弃，任务重要则要利用一些缓冲机制来处理
 *             - keepAliveTime和allowCoreThreadTimeout采用默认通常能满足
 */

@Component
@Slf4j
@EnableAsync
public class AsyncConfigurerImpl implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(10);//当前线程数
        threadPool.setMaxPoolSize(20);// 最大线程数
        threadPool.setQueueCapacity(30);//线程池所使用的缓冲队列
        threadPool.setWaitForTasksToCompleteOnShutdown(true);//等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setAwaitTerminationSeconds(60 * 15);// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setThreadNamePrefix("Async-");//  线程名称前缀
        //设置线程池中线程的拒绝策略
        /**
         * 所谓的拒绝策略就是 在线程池中的线程都有任务时，有新的异步任务时的处理 策略
         */
        threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPool.initialize(); // 初始化
        System.out.println("------------Async--------------》》》初始化Asvnc 异步线程池");
        return threadPool;

    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {

        return new MyAsyncExceptionHandler();
    }

    /**
     * 自定义异常处理类
     *
     * @author hry
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        //手动处理捕获的异常
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            System.out.println("-------------》》》捕获线程异常信息");
            log.error("Exception message - " + throwable.getMessage());
            log.error("Method name - " + method.getName());
            for (Object param : obj) {
                log.error("Parameter value - " + param);
            }
        }

    }
}
