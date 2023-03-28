package net.heavenmine.hmezcore.command;

import net.heavenmine.hmezcore.Main;
import net.heavenmine.hmezcore.file.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    private Main main;
    private ConfigFile configFile;
    public GamemodeCommand(Main main, ConfigFile configFile) {
        this.main = main;
        this.configFile = configFile;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String prefix = configFile.getPrefix();
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Lệnh này chỉ có thể được sử dụng bởi người chơi.");
            return true;
        }
        Player player = (Player) commandSender;

        if (!player.hasPermission("hmezcore.command.gamemode")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cBạn không có quyền sử dụng lệnh này!" ));
            return false;
        }
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("1")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Chuyển qua chế độ sáng tạo thành công!"));
                return true;
            } else if (args[0].equalsIgnoreCase("2")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Chuyển qua chế độ phiêu lưu thành công!"));
                return true;
            } else if (args[0].equalsIgnoreCase("0")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Chuyển qua chế độ sinh tồn thành công!"));
                return true;
            } else if (args[0].equalsIgnoreCase("3")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Chuyển qua chế độ theo dõi thành công!"));
                return true;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cTham số " + args[0] + " không tìm thấy"));
            return false;
        } else if (args.length == 2) {
            Player target = main.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix +" &cKhông tìm thấy người chơi có tên là &a" + args[1]));
            } else {
                if (args[0].equalsIgnoreCase("1")) {
                    target.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Thanh niên " + target.getDisplayName() + "đã chuyển qua chế độ sáng tạo thành công!"));
                    return true;
                } else if (args[0].equalsIgnoreCase("2")) {
                    target.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Thanh niên " + target.getDisplayName() + "đã chuyển qua chế độ phiêu lưu thành công!"));
                    return true;
                } else if (args[0].equalsIgnoreCase("0")) {
                    target.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Thanh niên " + target.getDisplayName() + "đã chuyển qua chế độ sinh tồn thành công!"));
                    return true;
                } else if (args[0].equalsIgnoreCase("3")) {
                    target.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Thanh niên " + target.getDisplayName() + "đã chuyển qua chế độ theo dõi thành công!"));
                    return true;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cTham số " + args[0] + " không tìm thấy"));
            }
            return false;
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &cLỗi: Thiếu tham số"));
        }

        return true;
    }
}
