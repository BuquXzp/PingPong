package xzp.pingpong.pong.pong.configuration;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xzp.pingpong.pong.pong.entity.PongData;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class PongServiceDisruptorConfiguration {


    @Bean
    public AtomicInteger disruptorSummary(){
        return new AtomicInteger();
    }

    @Bean
    public Disruptor<PongData> pongServiceDisruptor(AtomicInteger disruptorSummary){
        Disruptor<PongData> disruptor = new Disruptor<>(
            PongData::new,
            1024,
            Executors.defaultThreadFactory(),
            ProducerType.SINGLE,
            new YieldingWaitStrategy()
        );

        disruptor.handleEventsWith(new PongServiceDisruptorConsumer(disruptorSummary));
        disruptor.start();
        return disruptor;
    }


    @Data
    @Slf4j
    @RequiredArgsConstructor
    public static class PongServiceDisruptorConsumer implements EventHandler<PongData> {

        private final AtomicInteger disruptorSummary;

        @Override
        public void onEvent(PongData event, long sequence, boolean endOfBatch){
            log.info("Message consumed: {}",event.toString());
            disruptorSummary.incrementAndGet();
        }
    }
}


