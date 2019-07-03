package ml.loganhouston.mmocraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class MMOCommand implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        Logger logger = Main.getMMO().getLogger();
        Player player = (Player) sender;

        if (args.length < 2) {
            if (sender instanceof Player) sender.sendMessage(ChatColor.RED + "Please use /mmo help to see how to use this command.");
            else sender.sendMessage("Please use /mmo help to see how to use this command.");
            return true;
        }

        logger.info(args[0]);

        return false;
    }
}
