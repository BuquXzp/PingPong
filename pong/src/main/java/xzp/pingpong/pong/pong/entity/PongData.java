package xzp.pingpong.pong.pong.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class PongData {

    private String payload;
    private String filePath;
    private Instant instant;

}
