package com.birdwind.sharding.config.shardingAlgorithm.table;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        List<String> result = new ArrayList<>();

        Collection<Long> ids = getShardingValue(complexKeysShardingValue, "_id");
        Collection<Long> id2s = getShardingValue(complexKeysShardingValue, "_id");

        //t_user_0_1
        if (!ids.isEmpty() && !id2s.isEmpty()) {
            collection.forEach(table -> {
                result.add(String.valueOf(table));
            });
            return result;
        }

        // t_user_0_0~2
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                String user_1 = id % 3 + "_0";
                String user_2 = id % 3 + "_1";
                collection.forEach(table -> {
                    if (String.valueOf(table).endsWith(user_1) || String.valueOf(table).endsWith(user_2)) {
                        result.add(String.valueOf(table));
                    }
                });
            }

            //
        }else if(!id2s.isEmpty()){
            for (Long id : id2s) {
                String user_1 = "0_" + id % 3;
                String user_2 = "1_" + id % 3;
                collection.forEach(table -> {
                    if (String.valueOf(table).endsWith(user_1) || String.valueOf(table).endsWith(user_2)) {
                        result.add(String.valueOf(table));
                    }
                });
            }
        }

        return result;
    }

    private Collection<Long> getShardingValue(ComplexKeysShardingValue complexKeysShardingValue, String key) {
        Collection<Long> result = new ArrayList<>();
        if (complexKeysShardingValue.getColumnNameAndShardingValuesMap().size() > 0 && complexKeysShardingValue.getColumnNameAndShardingValuesMap()
                                                                                                               .containsKey(key)) {
            Collection<Long> value = (Collection<Long>) complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(key);
            result.addAll(value);
        }
        return result;
    }
}
