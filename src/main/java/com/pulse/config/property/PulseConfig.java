package com.pulse.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "pulse")
public class PulseConfig {

    private Database database;

    @Getter
    @Setter
    public static class Database {

        private H2 h2;

        @Getter
        @Setter
        public static class H2 {

            private String username;
            private String password;
            private String url;
            private String driverClass;
            private String dialect;
            private String showSql;
            private String hbm2DdlAuto;

        }

    }

}
