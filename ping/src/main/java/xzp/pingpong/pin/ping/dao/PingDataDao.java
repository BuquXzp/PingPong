package xzp.pingpong.pin.ping.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import xzp.pingpong.pin.ping.entity.PingData;

import java.time.Instant;

@Repository
public interface PingDataDao extends ReactiveMongoRepository<PingData, String> {

    Flux<PingData> findAllByInstantIsGreaterThanEqual(Instant instant);

}
