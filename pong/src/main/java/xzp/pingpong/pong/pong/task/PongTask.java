package xzp.pingpong.pong.pong.task;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.RecursiveDirectoryScanner;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.FileSystemPersistentAcceptOnceFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.metadata.ConcurrentMetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;
import xzp.pingpong.pong.pong.configuration.PongConfiguration;
import xzp.pingpong.pong.pong.entity.PongData;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@RequiredArgsConstructor
@Component
@EnableConfigurationProperties(PongConfiguration.class)
public class PongTask {


    private final PongConfiguration pongConfiguration;
    private final Disruptor<PongData> pongServiceDisruptor;

    @Bean
    public ConcurrentMetadataStore pingMetadataStore(){
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory(pongConfiguration.getWorkDir());
        return metadataStore;
    }

    @Bean
    @InboundChannelAdapter(channel = "pingInputChannel", poller = @Poller(fixedRate = "1000", maxMessagesPerPoll = "-1"))
    public MessageSource<File> pingMessageSource(ConcurrentMetadataStore metadataStore){
        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        FileSystemPersistentAcceptOnceFileListFilter filter = null;
        filters.addFilter(new SimplePatternFileListFilter("*.txt"));
        filters.addFilter(new FileSystemPersistentAcceptOnceFileListFilter(metadataStore,""));
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(pongConfiguration.getWorkDir()));
        source.setAutoCreateDirectory(true);
        RecursiveDirectoryScanner scanner = new RecursiveDirectoryScanner();
        scanner.setFilter(filters);
        source.setScanner(scanner);
        return source;
    }

    @Bean
    @ServiceActivator(inputChannel = "pingInputChannel")
    public MessageHandler handler() {
        return x -> {
            File payload = (File) x.getPayload();
            RingBuffer<PongData> buffer = pongServiceDisruptor.getRingBuffer();
            long next = buffer.next();
            PongData pongData = buffer.get(next);
            buildPongData(payload, pongData);
            buffer.publish(next);
        };
    }


    @SneakyThrows
    private void buildPongData(File file, PongData pongData){

        String payload = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String filePath = file.getAbsolutePath();
        Instant instant = Instant.ofEpochMilli(Long.parseLong(FilenameUtils.getBaseName(file.getName())));

        pongData.setPayload(payload);
        pongData.setFilePath(filePath);
        pongData.setInstant(instant);
    }



}
