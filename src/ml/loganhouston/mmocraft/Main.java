package ml.loganhouston.mmocraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ml.loganhouston.mmocraft.healthbars.DamageListener;
import ml.loganhouston.mmocraft.healthbars.DeathListeners;
import ml.loganhouston.mmocraft.healthbars.HealthBarCommands;
import ml.loganhouston.mmocraft.healthbars.HealthBarConfig;
import ml.loganhouston.mmocraft.healthbars.MetricsLite;
import ml.loganhouston.mmocraft.healthbars.MiscListeners;
import ml.loganhouston.mmocraft.healthbars.PlayerBar;
import ml.loganhouston.mmocraft.healthbars.Updater;
import utils.Debug;
import utils.PlayerBarUtils;
import utils.Utils;

import javax.management.openmbean.ArrayType;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Main plugin thing
 */

public class Main extends JavaPlugin implements Listener {

    public static JavaPlugin MMOcraft;
    public Logger logger = getLogger();
	// HealthBar Crap
	public static 	Main plugin;
	public static	Logger logger1;

	private static	DamageListener damageListener;
	private static	DeathListeners deathListener;
	private static 	MiscListeners miscListeners;
	// End of HealthBar Crap
    @Override
    public void onEnable() {

    		plugin = this;
    		logger = getLogger();


    		try {
    			String build = Utils.getBukkitBuild();
    			if (build != null) {
    				if (Integer.parseInt(build) < 2811) {
    					logger.warning("------------------------------------------");
    					logger.warning("Your Spigot build (#" + build + ") is old.");
    					logger.warning("HealthBar cannot work properly,");
    					logger.warning("please update Spigot.");
    					logger.warning("------------------------------------------");
    					this.setEnabled(false);
    					return;
    				}
    			}
    		} catch (Exception ignore) {}


    		damageListener = new DamageListener();
    		deathListener = new DeathListeners();
    		miscListeners = new MiscListeners();

    		//to check if I've forgot the debug on :)
    		Debug.color("�c[HealthBar] Debug ON");

    		//create the folder and the file
    		if (getDataFolder().exists()) {
    			getDataFolder().mkdir();
    		}
    		Utils.loadFile("config.yml", this);

    		//register events
    		getServer().getPluginManager().registerEvents(damageListener, this);
    		getServer().getPluginManager().registerEvents(deathListener, this);
    		getServer().getPluginManager().registerEvents(miscListeners, this);

    		//other files
    		reloadConfigFromDisk();
    		FileConfiguration config = getConfig();


    		//try to check updates
    		Updater.UpdaterHandler.setup(this, 54447, "�2[�aHealthBar�2] ", super.getFile(), ChatColor.GREEN, "/hbr update", "health-bar");

    		if (config.getBoolean("update-notification")) {
    			Thread updaterThread = new Thread(new Runnable() { public void run() {
    				Updater.UpdaterHandler.startupUpdateCheck();
    			}});

    			updaterThread.start();
    		}


    		//setup for command executor
    		getCommand("healthbar").setExecutor(new HealthBarCommands(this));

    		//metrics
    		try {
    			MetricsLite metrics = new MetricsLite(this);
    			metrics.start();
    		} catch (Exception e) {}


    //end of onEnable for HealthBar Crap
    	}


        logger.info("Plugin developed by Logan Houston and Levin202");
        logger.info("Initializing classes");
        void registerEvents(
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

        //HealthBar Crap
        PlayerBarUtils.removeAllHealthbarTeams(Bukkit.getScoreboardManager().getMainScoreboard());
		PlayerBar.removeBelowObj();
		DamageListener.removeAllMobHealthBars();
		System.out.println("HealthBar disabled, all the health bars have been removed.");
		//End of HealthBar Crap
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

	public void reloadConfigFromDisk() {

		reloadConfig();
		//Utils.checkDefaultNodes(getConfig(), this);
		HealthBarConfig.checkConfigYML();

		Utils.loadFile("custom-mob-bar.yml", this);
		Utils.loadFile("custom-player-bar.yml", this);
		Utils.loadFile("locale.yml", this);
		Utils.loadFile("config.yml", this);

		//forces to generate translations, if missing
		Utils.getTranslationMap(this);

		DamageListener.loadConfiguration();
		DeathListeners.loadConfiguration();
		PlayerBar.loadConfiguration();
		MiscListeners.loadConfiguration();
}
	public static MiscListeners getLoginListenerInstance() {
		return miscListeners;
	}

	public static File getPluginFile() {
		return plugin.getFile();
	}

//end of the HealthBar Crap in Main
}
