package com.birdwind.sharding.config.shardingAlgorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import java.util.Collection;

public class CustomPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String datasource = "datasource_";
        if (preciseShardingValue.getValue().equals("A")) {
            return datasource + "1";
        }
        return datasource + "0";
    }
}
