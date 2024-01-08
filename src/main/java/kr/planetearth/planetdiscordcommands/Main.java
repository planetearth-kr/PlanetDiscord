package kr.planetearth.planetdiscordcommands;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static Main instance;

    private static Logger log = Bukkit.getLogger();

    @Override
    public void onEnable() {
        instance = this;
        loadConfiguration();
        BotMain.main();

		log.info("§9PlanetDiscordCommands §ehas §aenabled§e.");
    }

    @Override
    public void onDisable() {
		log.info("§9PlanetDiscordCommands §ehas §cdisabled§e.");
    }

    public void loadConfiguration(){
        instance.getConfig().options().copyDefaults(false);
        instance.saveDefaultConfig();
    }
}
