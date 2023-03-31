package net.heavenmine.hmezcore;

import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.ChatColor;

public class Helper {
    private final ConfigFile configFile;

    public Helper(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public String Print (String string, final boolean has_prefix) {
        String prefix = configFile.getPrefix();
        return ChatColor.translateAlternateColorCodes('&', has_prefix ? prefix + string : string);
    }
    public String Print (String string) {
        String prefix = configFile.getPrefix();
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
