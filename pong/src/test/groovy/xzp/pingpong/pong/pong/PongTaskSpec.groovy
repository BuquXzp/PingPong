package xzp.pingpong.pong.pong

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.support.GenericMessage
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import xzp.pingpong.pong.pong.configuration.PongConfiguration
import xzp.pingpong.pong.pong.task.DisruptorSummaryTask
import xzp.pingpong.pong.pong.task.PongTask

@SpringBootTest
@EnableConfigurationProperties(PongConfiguration.class)
@RequiredArgsConstructor
class PongTaskSpec extends Specification {

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private DisruptorSummaryTask disruptorSummaryTask;
    @Autowired
    private PongTask pongTask;
    @Autowired
    private PongConfiguration pongConfiguration;



    def "Test of pong controller"(){
        given:
        def expectResult = "pong: Hello World!"

        when: "Get result"
        def result = restTemplate.getForEntity("http://localhost:10020/pong/helloWorld", String.class)

        then: "Check"
        with(result){
            (result.getBody() == "pong: Hello World!")
        }
    }

    def "Test of pong task"(){

        given:
        def handler = pongTask.handler()

        when: "Get result"
        def dir = pongConfiguration.getWorkDir()
        def fileOptional = Arrays.stream(new File(dir).listFiles())
            .map(x -> x.listFiles())
            .flatMap(Arrays::stream)
            .findAny()

        def file = null;
        if (fileOptional.isPresent()){
            file = fileOptional.get() as File;
        }else {
            throw new RuntimeException()
        }


        then: "Check"
        handler.handleMessage(new GenericMessage(file))
    }

}
