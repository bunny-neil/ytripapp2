package com.ytripapp.migration;

import com.ytripapp.migration.utils.Mappper;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class V2_0_1__AccountData implements SpringJdbcMigration {

    JdbcTemplate jdbcTemplate;

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        this.jdbcTemplate = jdbcTemplate;
        for (Map<String, Object> row : getAllOldUsers()) {
            String emailAddress = (String) row.get("email");
            if (emailAddress != null) {
                emailAddress = emailAddress.toLowerCase();
            }
            String password = (String) row.get("password");
            String firstName = (String) row.get("first_name");
            String lastName = (String) row.get("last_name");
            String gender = Mappper.mapGender((String) row.get("gender"));
            String phoneNo = (String) row.get("telephone");
            String occupation = (String) row.get("occupation");
            String introduction = (String) row.get("introduction");
            Date dateCreated = (Date) row.get("date_created");
            Date dateLastSignIn = (Date) row.get("date_last_login");
            Date dateUpdated = (Date) row.get("last_updated");
            boolean enabled = (Boolean) row.get("enabled");
            String facebookId = (String) row.get("facebook_id");
            String wechatId = (String) row.get("wechat_id");
            String stripeCustId = (String) row.get("stripe_customer_id");
            String nickname = (String) row.get("display_name");
            String wechatUnionId = (String) row.get("wechat_union_id");
            String iosDeviceId = (String) row.get("ios_device_token");
            String groupName = Mappper.mapGroupName((String) row.get("groupName"));
            String portraitUri = (String) row.get("portraitUri");
            Long newId = insertAccount(
                enabled,
                dateCreated,
                dateUpdated,
                dateLastSignIn,
                groupName,
                password,
                nickname,
                gender,
                portraitUri,
                phoneNo,
                firstName,
                lastName,
                occupation,
                introduction,
                stripeCustId,
                iosDeviceId,
                emailAddress
            );
            if (newId != null) {
                insertNewAuthorities(newId, groupName);
                if (!StringUtils.isEmpty(facebookId)) {
                    insertSocialConnection("Facebook", facebookId, newId);
                }
                if (!StringUtils.isEmpty(wechatUnionId)) {
                    insertSocialConnection("Wechat", wechatUnionId, newId);
                }
                else if (!StringUtils.isEmpty(wechatId)) {
                    insertSocialConnection("Wechat", wechatId, newId);
                }
            }
        }
    }

    Long insertAccount(boolean enabled,
                       Date dateCreated,
                       Date dateUpdated,
                       Date dateLastSignIn,
                       String groupName,
                       String password,
                       String nickname,
                       String gender,
                       String portraitUri,
                       String phoneNo,
                       String firstName,
                       String lastName,
                       String occupation,
                       String introduction,
                       String stripeCustId,
                       String apnsDeviceToken,
                       String emailAddress) {
        Long accountId = null;
        if (jdbcTemplate.queryForObject(
            "SELECT count(id) FROM ytripapp2.account_connections " +
                "WHERE connection_id = '" + emailAddress + "'", Long.class) == 0) {
            jdbcTemplate.update(
                "INSERT INTO ytripapp2.accounts(" +
                    "version, " +
                    "enabled, " +
                    "date_created, " +
                    "date_updated, " +
                    "date_last_sign_in, " +
                    "group_name, " +
                    "`password`, " +
                    "nickname, " +
                    "gender, " +
                    "portrait_uri, " +
                    "phone_no, " +
                    "first_name, " +
                    "last_name, " +
                    "occupation, " +
                    "introduction, " +
                    "stripe_cust_id, " +
                    "apns_device_token) " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                0L,
                enabled,
                dateCreated,
                dateUpdated,
                dateLastSignIn,
                groupName,
                password,
                nickname,
                gender,
                portraitUri,
                phoneNo,
                firstName,
                lastName,
                occupation,
                introduction,
                stripeCustId,
                apnsDeviceToken);
            accountId = jdbcTemplate.queryForObject("SELECT max(id) FROM ytripapp2.accounts", Long.class);

            if (!StringUtils.isEmpty(emailAddress)) {
                jdbcTemplate.update(
                    "INSERT INTO account_connections(connection_type, provider_name, connection_id, external_link, account_id) VALUES (?, ?, ?, ?, ?)",
                    "Email",
                    "Native",
                    emailAddress,
                    null,
                    accountId
                );
            }
        }
        return accountId;
    }

    void insertNewAuthorities(Long accountId, String grouopName) {
        String authority = null;
        if ("Guest".equals(grouopName)) {
            authority = "ROLE_GUEST";
        }
        if ("Editor".equals(grouopName)) {
            authority = "ROLE_EDITOR";
        }
        if ("Host".equals(grouopName)) {
            authority = "ROLE_HOST";
        }
        if ("Gateway".equals(grouopName)) {
            authority = "ROLE_GATEWAY";
        }

        jdbcTemplate.update("INSERT INTO ytripapp2.account_authorities(account_id, authority) VALUES (?, ?)", accountId, authority);
    }

    void insertSocialConnection(String providerName, String connectionId, Long accountId) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM ytripapp2.account_connections WHERE connection_id = '" + connectionId + "'", Long.class) == 0) {

            jdbcTemplate.update(
                "INSERT INTO ytripapp2.account_connections(connection_type, connection_id, provider_name, account_id) VALUES (?, ?, ?, ?)",
                "External", connectionId, providerName, accountId
            );

        }
    }

    List<Map<String, Object>> getAllOldUsers() {
        return jdbcTemplate.queryForList(
            "SELECT u.*, g.name AS groupName, i.path AS portraitUri " +
                "FROM ytripapp.users u INNER JOIN ytripapp.groups g ON u.group_id = g.id " +
                "                      LEFT JOIN ytripapp.images i ON u.portrait_image_id = i.id " +
                "WHERE g.name IN ('GROUP_GUEST', 'GROUP_HOST', 'GROUP_EDITOR')"
        );
    }

    void addGatewayUser() {
        Long newId = insertAccount(
            true,
            new Date(),
            null,
            null,
            "Gateway",
            "gateway@ytripapp.com",
            "$2a$08$OejaVvoDWSYv97UZOWgZOeo7axINLcksxMMoXta90HQfJcnC/KVte", // bcrypt('2TOIQIZEoBVu3sCgPJvZ')
            "gateway",
            "Unspecified",
            null,
            null,
            "Gateway",
            "Gateway",
            null,
            null,
            null,
            null
        );
        insertNewAuthorities(newId, "Gateway");
    }
}