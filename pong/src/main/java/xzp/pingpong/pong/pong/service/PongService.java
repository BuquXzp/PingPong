package xzp.pingpong.pong.pong.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import xzp.pingpong.pong.pong.configuration.PongConfiguration;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(PongConfiguration.class)
public class PongService {

    private final PongConfiguration pongConfiguration;


    @SneakyThrows
    @PostConstruct
    public void init(){
        String workDir = pongConfiguration.getWorkDir();
        if (StringUtils.isBlank(workDir)){
            log.error("Configuration of ping.work-dir is blank, exit");
            throw new RuntimeException("Configuration of ping.work-dir is blank");
        }
        File file = new File(workDir);
        if (!file.exists()){
            log.info("Work Dir config does not exist, it will be created: {}", workDir);
            FileUtils.forceMkdir(file);
        }
    }
}
