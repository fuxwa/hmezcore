package net.heavenmine.hmezcore.file;
import net.heavenmine.hmezcore.Main;

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
}
