package xzp.pingpong.pong.pong.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ping")
public class PongConfiguration {

    private String workDir;

    private String fileNamePatten;

}
