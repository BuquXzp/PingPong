package xzp.pingpong.pin.ping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("ping")
public class PingController {


    @GetMapping("helloWorld")
    public Mono<String> helloWorld(){
        return Mono.just("ping: Hello World!");
    }

}
