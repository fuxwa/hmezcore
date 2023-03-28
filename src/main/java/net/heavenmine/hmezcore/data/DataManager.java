package net.heavenmine.hmezcore.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import net.heavenmine.hmezcore.modal.PlayerData;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String mysql_url = "jdbc:mysql://" + host  + ":" + port + "/" + database + "?autoReconnect=true";
        String sqllite_url = "jdbc:sqlite:"+ dataFolder +"/database.db";
        String url = typeData.equalsIgnoreCase("mysql") ? mysql_url : sqllite_url;
        HikariConfig config = new HikariConfig();
        if(typeData.equalsIgnoreCase("mysql")) {

        } else {
            config.setDriverClassName("org.sqlite.JDBC");
        }
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
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
    public void insertData (String username, String uuid, String ip_address, String firstjoin, String lastjoin, String location) {
        String sql = "INSERT INTO player_data (username, uuid, ip_address, firstjoin, lastjoin, location) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, uuid);
            statement.setString(3, ip_address);
            statement.setString(4, firstjoin);
            statement.setString(5, lastjoin);
            statement.setString(6, location);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to create table player_data\n" + e);
        }
    }
    public PlayerData getPlayer(String uuid) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_data WHERE uuid = ?");
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String ip_address = resultSet.getString("ip_address");
                String firstjoin = resultSet.getString("firstjoin");
                String lastjoin = resultSet.getString("lastjoin");
                String location = resultSet.getString("location");

                return new PlayerData(id, username, uuid,ip_address,firstjoin,lastjoin,location);
            }
        } catch (SQLException e) {
            main.getLogger().warning("Đã xảy ra lỗi khi tìm kiếm người chơi: " + e.getMessage());
        }

        return null;
    }
    public void updatePlayer (String uuid, String lastjoin, String location) {
        String sql = "UPDATE player_data SET lastjoin = ?, location = ? WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lastjoin);
            statement.setString(2, location);
            statement.setString(3, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to create table player_data\n" + e);
        }
    }

}
