package xzp.pingpong.pong.pong.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class DisruptorSummaryTask {


    private final AtomicInteger disruptorSummary;


    @Scheduled(fixedRate = 1000)
    public void report(){
        log.info("Disruptor summary: {}",disruptorSummary.getAndSet(0));
    }
}
