package io.github.ruan_pablo_oli.library.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataBaseConfiguration {


    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;


    //@Bean // Criação de datasource simples, não recomendado para produção!
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean // Criação de datasource recomendado para produção
    public DataSource hikariDataSource(){


        log.info("Iniciando conexão com banco na URL: {}",url);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // Máximo de conexões liberadas
        config.setMinimumIdle(1); // Mínimo de conexões liberadas
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600 mil ms, 10 minutos de duração de conexão
        config.setConnectionTimeout(100000); // Timeout para conseguir conexão
        config.setConnectionTestQuery("select 1"); // Teste se está conectando com o banco



        return new HikariDataSource(config);

    }


}
