package xzp.pingpong.pin.ping.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ping")
public class PingConfiguration {

    private String workDir;

    private String fileNamePatten;

}
