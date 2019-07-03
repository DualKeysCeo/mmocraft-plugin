package ml.loganhouston.mmocraft;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.management.openmbean.ArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {

    public static JavaPlugin MMOcraft;
    public Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("Plugin developed by Logan Houston and Levin202");
        logger.info("Initializing classes");
        registerEvents(
                this
        );
        logger.info("Classes initialized");
        logger.info("Initializing commands");
        String[] commands = {"mmo"};
        registerCommands(commands, new MMOCommand());
        logger.info("Commands Initialized");
    }

    @Override
    public void onDisable() {
        logger.warning("Shutting down...");
        MMOcraft = null; // prevents memory leaks
    }

    public static void registerEvents(Listener... listeners) {
        for (Listener listener : listeners)
            Bukkit.getServer().getPluginManager().registerEvents(listener, MMOcraft);
    }

    public static void registerCommands(String[] commandNames, CommandExecutor... commandExecutors) {
        int i = 0;
        for (CommandExecutor commandExecutor : commandExecutors) {
            MMOcraft.getCommand(commandNames[i]).setExecutor(commandExecutor);
            i++;
        }
    }

    public static Plugin getMMO() { return MMOcraft; }
    public Logger getLog() { return logger; }
}
