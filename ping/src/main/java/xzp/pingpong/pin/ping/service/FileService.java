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
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(PingConfiguration.class)
@Slf4j
public class FileService {
    private final PingConfiguration pingConfiguration;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").withZone(ZoneId.systemDefault());

    @SneakyThrows
    public File writeNewFile(Instant instant, String payload){
        long timeStamp = instant.getEpochSecond();
        String folderName = dateTimeFormatter.format(instant);
        File folder = new File(pingConfiguration.getWorkDir(), folderName);

        if (!folder.exists()){
            FileUtils.forceMkdir(folder);
        }

        File file = new File(folder, timeStamp + ".txt");
        FileUtils.write(file, payload, StandardCharsets.UTF_8,true);

        return file;
    }




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
