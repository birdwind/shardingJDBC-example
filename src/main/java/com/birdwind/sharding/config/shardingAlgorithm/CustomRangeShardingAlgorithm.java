package com.birdwind.sharding.config.shardingAlgorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomRangeShardingAlgorithm implements RangeShardingAlgorithm<Integer> {

    // 區間為1~5返回datasource_0
    // 區間為6~10返回datasource_1
    // 區間為1~10返回datasource_0, datasource_1
    @Override
    public Collection<String> doSharding(Collection<String> databaseNames, RangeShardingValue<Integer> rangeShardingValue) {
        Set<String> result = new LinkedHashSet<>();
        String datasource = "datasource_";
        if (Range.closed(1, 5).encloses(rangeShardingValue.getValueRange())) {
            result.add(datasource + 0);
        } else if (Range.closed(6, 10).encloses(rangeShardingValue.getValueRange())) {
            result.add(datasource + 1);
        } else if (Range.closed(1, 10).encloses(rangeShardingValue.getValueRange())) {
            result.addAll(databaseNames);
        } else {
            throw new UnsupportedOperationException();
        }
        return result;
    }
}
