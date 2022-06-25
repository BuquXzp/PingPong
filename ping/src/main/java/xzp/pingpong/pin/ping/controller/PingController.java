package xzp.pingpong.pin.ping.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xzp.pingpong.pin.ping.dao.PingDataDao;
import xzp.pingpong.pin.ping.entity.PingData;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping("ping")
@RequiredArgsConstructor
@Valid
public class PingController {

    private final PingDataDao pingDataDao;

    @GetMapping("helloWorld")
    public Mono<String> helloWorld(){
        return Mono.just("ping: Hello World!");
    }


    @GetMapping("list")
    public Flux<PingData> list(){
        return pingDataDao.findAll();
    }

    @GetMapping("findAllByInstantIsGreaterThanEqual")
    public Flux<PingData> findAllByInstantIsGreaterThanEqual(Instant instant){
        return pingDataDao.findAllByInstantIsGreaterThanEqual(instant);
    }

    @GetMapping("findById")
    public Mono<PingData> findById(String id){
        return pingDataDao.findById(id);
    }

}
