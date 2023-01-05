package com.birdwind.sharding.config.shardingAlgorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import java.util.Collection;

public class CustomHintShardingAlgorithm implements HintShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection collection, HintShardingValue hintShardingValue) {
        // TODO: 透過設定HintManager，來設定LogicTable，強制路由才能有用
        return null;
    }
}
