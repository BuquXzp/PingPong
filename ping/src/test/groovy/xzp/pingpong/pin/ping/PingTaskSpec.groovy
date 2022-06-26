package xzp.pingpong.pin.ping

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import xzp.pingpong.pin.ping.configuration.PingConfiguration
import xzp.pingpong.pin.ping.dao.PingDataDao
import xzp.pingpong.pin.ping.service.FileService
import xzp.pingpong.pin.ping.task.PingTask

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SpringBootTest
@EnableConfigurationProperties(PingConfiguration.class)
class PingTaskSpec extends Specification {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private FileService fileService;
    @Autowired
    private PingDataDao pingDataDao;
    @Autowired
    private PingTask pingTask;
    @Autowired
    private PingConfiguration pingConfiguration;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").withZone(ZoneId.systemDefault());


    def "Test PingTask executed"(){
        given: "Set Instant To Run Task"
        def instant = Instant.now();
        def payload = "payloadForTest";

        when: "Run PingTask"
        pingTask.run();

        then: "Check File generated"
        def file = new File(pingConfiguration.getWorkDir())
        with(file){
            file.list().length != 0
        }

    }


    def "Test of ping controller"(){
        given:
        def expectResult = "ping: Hello World!"

        when: "Get result"
        def result = restTemplate.getForEntity("http://localhost:10011/ping/helloWorld", String.class)

        then: "Check"
        with(result){
             (result.getBody() == "ping: Hello World!")
        }
    }

}
