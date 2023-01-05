package com.birdwind.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.birdwind.sharding.config.shardingAlgorithm.CustomPreciseShardingAlgorithm;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.core.strategy.masterslave.RoundRobinMasterSlaveLoadBalanceAlgorithm;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
@Slf4j
public class ShardingJDBCConfig {

    @Bean
    public DataSource dataSourceConfig() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        // 主从
        shardingRuleConfiguration.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());
        shardingRuleConfiguration.setDefaultDataSourceName("datasource_0");
        // 分表
        shardingRuleConfiguration.setTableRuleConfigs(getTableRuleConfiguration());
        shardingRuleConfiguration.getBindingTableGroups().add("t_user");

        // 默认分表配置
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("_id", "t_user_$->{_id % 3}"));
        // 默認分库配置
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("_brand", new CustomPreciseShardingAlgorithm()));
        // shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig("_id, _id", new ComplexShardingStrategyConfiguration());


        // 默認ID產生策略
        shardingRuleConfiguration.setDefaultKeyGeneratorConfig(getKeyGeneratorConfiguration("_id"));

        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");

        return ShardingDataSourceFactory.createDataSource(generatorDataSourceMap(), shardingRuleConfiguration, properties);
    }

    // 定义主键生成策略
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration(String column) {
        return new KeyGeneratorConfiguration("SNOWFLAKE", column);
    }

    // 分片表配置
    private TableRuleConfiguration getTableRuleConfiguration(String table, String keyColumn, String dataNode, String strategy) {
        TableRuleConfiguration result = new TableRuleConfiguration(table, dataNode);

        // 單表分片策略
        // result.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration(table, strategy));

        // 單表分庫策略
        // result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration(table, strategy));

        // 單表ID產生策略
        // result.setKeyGeneratorConfig(getKeyGeneratorConfiguration(keyColumn));
        return result;
    }

    // 定义数据源
    private Map<String, DataSource> generatorDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

        // 配置第一个数据源
        dataSourceMap.put("ds1", initDataSource("127.0.0.1", "3310", "root", "123456", "shardingjdbc"));

        // 配置第一个数据源從庫
        dataSourceMap.put("sl1", initDataSource("127.0.0.1", "3311", "root", "123456", "shardingjdbc"));

        // 配置第二个数据源
        dataSourceMap.put("datasource_1", initDataSource("127.0.0.1", "3312", "root", "123456", "shardingjdbc"));
        return dataSourceMap;
    }

    // 主从设定
    private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("datasource_0", "ds1", Arrays.asList("sl1"), new LoadBalanceStrategyConfiguration(new RoundRobinMasterSlaveLoadBalanceAlgorithm().getType()));
        return Lists.newArrayList(masterSlaveRuleConfiguration);
    }

    // 定义分片策略
    private List<TableRuleConfiguration> getTableRuleConfiguration() {
        return Lists.newArrayList(getTableRuleConfiguration("t_user", "_id", "datasource_$->{0..1}.t_user_$->{0..2}", "t_user_${_id % 3}"));
    }

    private DataSource initDataSource(String host, String port, String username, String password, String dbName) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&tinyInt1isBit=false&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true", host, port, dbName));
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }


    // 事务管理器
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource shardingDataSource) {
        return new DataSourceTransactionManager(shardingDataSource);
    }
}
