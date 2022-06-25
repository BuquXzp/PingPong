package xzp.pingpong.pin.ping.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import xzp.pingpong.pin.ping.configuration.PingConfiguration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(PingConfiguration.class)
@Slf4j
public class PingService {

    private final PingConfiguration pingConfiguration;


    @SneakyThrows
    @PostConstruct
    public void init(){
        String workDir = pingConfiguration.getWorkDir();
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
