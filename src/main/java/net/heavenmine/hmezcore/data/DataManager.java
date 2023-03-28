package net.heavenmine.hmezcore.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataManager {
    private Main main;
    private final ConfigFile configFile;
    public DataManager(Main main,ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }
    private HikariDataSource dataSource;
    public void onLoad() {
        String host = configFile.getHost();
        String port = configFile.getPort();
        String database = configFile.getDbName();
        String username = configFile.getUsername();
        String password = configFile.getPassword();
        String typeData = configFile.getTypeData();
        File dataFolder = main.getDataFolder();
        String mysql_url = "jdbc:mysql:// +" + host  + ":" + port + "/" + database + "?autoReconnect=true";
        String sqllite_url = "jdbc:sqlite:"+ dataFolder +"/database.db";
        String url = typeData.equalsIgnoreCase("mysql") ? mysql_url : sqllite_url;
        HikariConfig config = new HikariConfig();
        if(typeData.equalsIgnoreCase("mysql")) {

        } else {
            config.setDriverClassName("org.sqlite.JDBC");
//            File databaseFile = new File(dataFolder, "database.db");
//            if (!databaseFile.exists()) {
//                try {
//                    databaseFile.createNewFile();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
        }
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
        String sqlite_query = "CREATE TABLE IF NOT EXISTS player_data (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    username TEXT," +
                "    uuid TEXT," +
                "    ip_address TEXT," +
                "    firstjoin TEXT," +
                "    lastjoin TEXT," +
                "    location TEXT" +
                ");";
        String mysql_query = "CREATE TABLE IF NOT EXISTS player_data (id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), uuid VARCHAR(255), ip_address VARCHAR(255), firstjoin VARCHAR(255), lastjoin VARCHAR(255), location VARCHAR(255));";
        String sql = typeData.equalsIgnoreCase("mysql") ? mysql_query : sqlite_query;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to create table player_data\n" + e);
        }
    }

}
