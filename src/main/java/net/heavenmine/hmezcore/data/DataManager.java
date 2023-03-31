package net.heavenmine.hmezcore.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import net.heavenmine.hmezcore.modal.HomePlayerData;
import net.heavenmine.hmezcore.modal.PlayerData;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String sqlite_player_data = "CREATE TABLE IF NOT EXISTS player_data (" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    username TEXT," +
                "    uuid TEXT," +
                "    ip_address TEXT," +
                "    firstjoin TEXT," +
                "    lastjoin TEXT," +
                "    world TEXT," +
                "    x TEXT," +
                "    y TEXT," +
                "    z TEXT" +
                ");";
        String sqlite_home_data = "CREATE TABLE IF NOT EXISTS home_data ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    username TEXT," +
                "    uuid TEXT," +
                "    homeName TEXT," +
                "    world TEXT," +
                "    x TEXT," +
                "    y TEXT," +
                "    z TEXT" +
                ");";
        String sqlite_query = sqlite_home_data + sqlite_player_data;
        String mysql_player_data = "CREATE TABLE IF NOT EXISTS player_data (id INT PRIMARY KEY AUTO_INCREMENT," +
                " username VARCHAR(255), uuid VARCHAR(255), ip_address VARCHAR(255), firstjoin VARCHAR(255)," +
                " lastjoin VARCHAR(255), world VARCHAR(255), x VARCHAR(255), y VARCHAR(255)," +
                " z VARCHAR(255));";
        String mysql_home_data = "CREATE TABLE IF NOT EXISTS home_data ("
                + "id INT PRIMARY KEY AUTO_INCREMENT," +
                "    username VARCHAR(255)," +
                "    uuid VARCHAR(255)," +
                "    homeName VARCHAR(255)," +
                "    world VARCHAR(255)," +
                "    x VARCHAR(255)," +
                "    y VARCHAR(255)," +
                "    z VARCHAR(255)" +
                ");";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement_player_data = connection.prepareStatement(typeData.equalsIgnoreCase("mysql") ? mysql_player_data : sqlite_player_data);
            statement_player_data.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to create table\n" + e);
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement_home_data = connection.prepareStatement(typeData.equalsIgnoreCase("mysql") ? mysql_home_data : sqlite_home_data);
            statement_home_data.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to create table\n" + e);
        }
    }
    public void insertPlayerData (String username, String uuid, String ip_address,
                            String firstjoin, String lastjoin,
                            String world, String x, String y, String z) {
        String sql = "INSERT INTO player_data (username, uuid, ip_address," +
                " firstjoin, lastjoin, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, uuid);
            statement.setString(3, ip_address);
            statement.setString(4, firstjoin);
            statement.setString(5, lastjoin);
            statement.setString(6, world);
            statement.setString(7, x);
            statement.setString(8, y);
            statement.setString(9, z);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to insert data\n" + e);
        }
    }
    public HomePlayerData insertHomeData(String username, String uuid, String homeName,
                                         String world, String x, String y, String z) {
        String checkSql = "SELECT COUNT(*) AS count FROM home_data WHERE username = ? AND homeName = ?";
        String insertSql = "INSERT INTO home_data (username, uuid, homeName, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            // Check if homeName already exists for this player
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, username);
            checkStatement.setString(2, homeName);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                return null;
            }
            // Insert new home into home_data table
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setString(1, username);
            insertStatement.setString(2, uuid);
            insertStatement.setString(3, homeName);
            insertStatement.setString(4, world);
            insertStatement.setString(5, x);
            insertStatement.setString(6, y);
            insertStatement.setString(7, z);
            insertStatement.executeUpdate();
            // Return the new HomePlayerData object
            return new HomePlayerData(null, username, uuid, homeName, world, x, y, z);
        } catch (SQLException e) {
            System.out.println("Failed to insert home data\n" + e);
            return null;
        }
    }

    public PlayerData getPlayerData(String uuid) {
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
                String world = resultSet.getString("world");
                String x = resultSet.getString("x");
                String y = resultSet.getString("y");
                String z = resultSet.getString("z");

                return new PlayerData(id, username, uuid, ip_address, firstjoin, lastjoin, world, x, y, z);
            }
        } catch (SQLException e) {
            main.getLogger().warning("Đã xảy ra lỗi khi tìm kiếm người chơi: " + e.getMessage());
        }
        return null;
    }
    public List<HomePlayerData> getHomeData(String uuid) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM home_data WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            List<HomePlayerData> data = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String homeName = resultSet.getString("homeName");
                String world = resultSet.getString("world");
                String x = resultSet.getString("x");
                String y = resultSet.getString("y");
                String z = resultSet.getString("z");
                HomePlayerData homeData = new HomePlayerData(id, username, uuid, homeName, world, x, y, z);
                data.add(homeData);
            }
            return data;
        } catch (SQLException e) {
            main.getLogger().warning("Đã xảy ra lỗi khi tìm kiếm người chơi: " + e.getMessage());
        }
        return null;
    }
    public void updatePlayerData (String uuid, String lastjoin, String world, String x, String y, String z) {
        String sql = "UPDATE player_data SET lastjoin = ?, world = ?, x = ?, y = ?, z = ? WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, lastjoin);
            statement.setString(2, world);
            statement.setString(3, x);
            statement.setString(4, y);
            statement.setString(5, z);
            statement.setString(6, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update data\n" + e);
        }
    }
    public void updateHomeData (String uuid, String homeName, String world, String x, String y, String z) {
        String sql = "UPDATE home_data SET homeName = ?, world = ?, x = ?, y = ?, z = ? WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, homeName);
            statement.setString(2, world);
            statement.setString(3, x);
            statement.setString(4, y);
            statement.setString(5, z);
            statement.setString(6, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update home data\n" + e);
        }
    }
    public void deleteHome (String uuid, String homeName) {
        String sql = "DELETE FROM home_data" +
                " WHERE uuid = ? AND homeName = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, homeName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update home data\n" + e);
        }
    }
    public HomePlayerData findHome (String uuid, String homeName) {
        String sql = "SELECT * FROM home_data" +
                " WHERE uuid = ? AND homeName = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, uuid);
            statement.setString(2, homeName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String world = resultSet.getString("world");
                String x = resultSet.getString("x");
                String y = resultSet.getString("y");
                String z = resultSet.getString("z");

                return new HomePlayerData(id,username, uuid, homeName, world, x, y, z);
            }
        } catch (SQLException e) {
            System.out.println("Failed to update home data\n" + e);
        }
        return null;
    }

}
