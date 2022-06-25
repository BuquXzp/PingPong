package xzp.pingpong.pin.ping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PingData {

    private String id;
    private Instant instant;
    private String payload;

}
