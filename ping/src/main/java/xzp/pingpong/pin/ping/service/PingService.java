package xzp.pingpong.pin.ping.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import xzp.pingpong.pin.ping.configuration.PingConfiguration;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(PingConfiguration.class)
@Slf4j
public class PingService {



}
