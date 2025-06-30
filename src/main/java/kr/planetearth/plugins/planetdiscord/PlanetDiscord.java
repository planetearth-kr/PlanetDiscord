package kr.planetearth.plugins.planetdiscord.planetdiscord;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class PlanetDiscord extends JavaPlugin {

    public static PlanetDiscord instance;

    private static Logger log = Bukkit.getLogger();

    @Override
    public void onEnable() {
        instance = this;
        loadConfiguration();
        Bot.main();

		log.info("§9PlanetDiscord §ehas §aenabled§e.");
    }

    @Override
    public void onDisable() {
		log.info("§9PlanetDiscord §ehas §cdisabled§e.");
    }

    public void loadConfiguration(){
        instance.getConfig().options().copyDefaults(false);
        instance.saveDefaultConfig();
    }
}
