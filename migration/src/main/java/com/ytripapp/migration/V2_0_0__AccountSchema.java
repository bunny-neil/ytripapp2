package com.ytripapp.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V2_0_0__AccountSchema implements SpringJdbcMigration {

    JdbcTemplate jdbcTemplate;

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        this.jdbcTemplate = jdbcTemplate;
        accountTable();
        accountConnectionTable();
        accountAuthorities();
    }

    void accountTable() {
        jdbcTemplate.execute(
            "CREATE TABLE ytripapp2.accounts (" +
                "  id                   BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "  version              BIGINT NOT NULL," +
                "  enabled              TINYINT(1)," +
                "  group_name           VARCHAR(31) NOT NULL," +
                "  date_created         DATETIME," +
                "  date_updated         DATETIME," +
                "  date_last_sign_in    DATETIME," +
                "  `password`           VARCHAR(127)," +
                "  stripe_cust_id       VARCHAR(63)," +
                "  apns_device_token    VARCHAR(255)," +
                "  nickname             VARCHAR(31) CHARACTER SET utf8mb4 NOT NULL," +
                "  first_name           VARCHAR(31) CHARACTER SET utf8mb4," +
                "  last_name            VARCHAR(31) CHARACTER SET utf8mb4," +
                "  gender               VARCHAR(15)," +
                "  phone_no             VARCHAR(31)," +
                "  occupation           VARCHAR(63) CHARACTER SET utf8mb4," +
                "  introduction         LONGTEXT," +
                "  portrait_uri         VARCHAR(511)" +
            ")"
        );
    }

    void accountConnectionTable() {
        jdbcTemplate.execute(
            "CREATE TABLE ytripapp2.account_connections (" +
                "  id                   BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "  connection_type      VARCHAR(31) NOT NULL," +
                "  provider_name        VARCHAR(31) NOT NULL," +
                "  connection_id        VARCHAR(255) UNIQUE NOT NULL," +
                "  external_link        VARCHAR(511)," +
                "  account_id           BIGINT" +
                ")"
        );
        jdbcTemplate.execute(
            "ALTER TABLE ytripapp2.account_connections " +
                "ADD CONSTRAINT account_connections_fk_account_id FOREIGN KEY (account_id) REFERENCES accounts(id)"
        );
    }

    void accountAuthorities() {
        jdbcTemplate.execute(
            "CREATE TABLE ytripapp2.account_authorities (" +
                "  account_id       BIGINT NOT NULL, " +
                "  authority        VARCHAR(31) NOT NULL" +
                ")"
        );
        jdbcTemplate.execute(
            "ALTER TABLE ytripapp2.account_authorities " +
                "ADD CONSTRAINT account_authorities_fk_account_id FOREIGN KEY (account_id) REFERENCES accounts(id)"
        );
    }
}
