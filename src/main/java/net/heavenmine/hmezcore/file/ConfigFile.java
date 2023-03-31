package net.heavenmine.hmezcore.file;
import net.heavenmine.hmezcore.Main;

import java.io.File;
import java.io.IOException;

public class ConfigFile {
    private Main main;
    public ConfigFile(Main main) {
        this.main = main;
    }
//    YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(main.getDataFolder(), "config.yml"));
    public String getVersion() {
        return main.getConfig().getString("version");
    }

    public String getPrefix() {
        return main.getConfig().getString("prefix");
    }
    public String getTypeData() {
        return main.getConfig().getString("storage.type");
    }
    public String getHost() {
        return main.getConfig().getString("storage.host");
    }
    public String getPort() {
        return main.getConfig().getString("storage.port");
    }
    public String getUsername() {
        return main.getConfig().getString("storage.username");
    }
    public String getPassword() {
        return main.getConfig().getString("storage.password");
    }
    public String getDbName() {
        return main.getConfig().getString("storage.dbname");
    }
    public String getWorldSpawn() {
        return main.getConfig().getString("spawn.world");
    }
    public void setWorldSpawn(String world) throws IOException {
        main.getConfig().set("spawn.world",world);
//        main.getConfig().save(new File(main.getDataFolder(), "config.yml"));
    }
    public Double getX() {
        return main.getConfig().getDouble("spawn.x");
    }
    public void setX(Double x) throws IOException {
        main.getConfig().set("spawn.x", x );
//        main.getConfig().save(new File(main.getDataFolder(), "config.yml"));
    }
    public Double getY() {
        return main.getConfig().getDouble("spawn.y");
    }
    public void setY(Double y) throws IOException {
        main.getConfig().set("spawn.y", y);
//        main.getConfig().save(new File(main.getDataFolder(), "config.yml"));
    }
    public Double getZ() {
        return main.getConfig().getDouble("spawn.z");
    }
    public void setZ(Double z) throws IOException {
        main.getConfig().set("spawn.z", z);
//        main.getConfig().save(new File(main.getDataFolder(), "config.yml"));
    }
    public Double getPitch() {
        return main.getConfig().getDouble("spawn.pitch");
    }
    public void setPitch(Float pitch) throws IOException {
        main.getConfig().set("spawn.pitch", pitch);
//        main.getConfig().save(new File(main.getDataFolder(), "config.yml"));
    }
    public Double getYaw() {
        return main.getConfig().getDouble("spawn.yaw");
    }
    public void setYaw(Float yaw) throws IOException {
        main.getConfig().set("spawn.yaw", yaw);
//        main.getConfig().save(new File(main.getDataFolder(), "config.yml"));
    }
}
