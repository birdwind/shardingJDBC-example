package com.birdwind.sharding.config;

import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import java.util.Properties;

public class CustomShardingKeyGenerator implements ShardingKeyGenerator {

    private Properties properties = new Properties();

    @Override
    public Comparable<?> generateKey() {
        //TODO: 自定義生成方式
        return null;
    }

    @Override
    public String getType() {
        return "CUSTOM";
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
