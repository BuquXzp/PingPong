package xzp.pingpong.pin.ping.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xzp.pingpong.pin.ping.dao.PingDataDao;
import xzp.pingpong.pin.ping.entity.PingData;
import xzp.pingpong.pin.ping.service.FileService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class PingTask {

    private final FileService fileService;
    private final PingDataDao pingDataDao;

    @Scheduled(cron = "${ping.cron}")
    public void run(){
        String payload = "hello";
        Instant instant = Instant.now();
        fileService.writeNewFile(instant, payload);
        pingDataDao.save(new PingData(null, instant, payload)).subscribe();
        log.info("Ping task executed");
    }



}
