package xzp.pingpong.pong.pong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("pong")
public class PongController {

    @GetMapping("helloWorld")
    public Mono<String> helloWorld(){
        return Mono.just("pong: Hello World!");
    }
}
