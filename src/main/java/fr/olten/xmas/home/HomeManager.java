package fr.olten.xmas.home;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class HomeManager {

    private final static Logger LOGGER = Logger.getLogger("HomeManager");
    private File homeFolder;

    public void setup(Plugin plugin){
        LOGGER.info("Starting setup (Plugin: " + plugin.getName() + ")");
        this.homeFolder = new File(plugin.getDataFolder(), "homes");

        if (!this.homeFolder.exists()) {
            LOGGER.info("Homes folder doesn't exist, creating it...");
            this.homeFolder.mkdirs();
            LOGGER.info("Homes folder created!");
        }
        LOGGER.info("Setup completed!");
    }

    public void init(Player player){
        File file = new File(this.homeFolder, player.getUniqueId().toString());

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
        conf.addDefault("name", player.getName());
    }

    public void checkName(Player player){
        YamlConfiguration conf = getConfiguration(player);
        if(!conf.getString("name").equals(player.getName())){
            conf.set("name", player.getName());
        }
    }

    public boolean exists(Player player){
        return this.getConfiguration(player) != null;
    }

    public void add(Player player, Home home){
        //TODO : add logs
        YamlConfiguration conf = getConfiguration(player);
        List<String> homes = conf.getStringList("homes");
        homes.add(home.name());
        conf.set("homes", homes);

        conf.set("homes." + home.name() + "x", home.location().getX());
        conf.set("homes." + home.name() + "y", home.location().getY());
        conf.set("homes." + home.name() + "z", home.location().getZ());
        conf.set("homes." + home.name() + "world_name", home.location().getWorld().getName());
        conf.set("homes." + home.name() + "yaw", home.location().getYaw());
        conf.set("homes." + home.name() + "pitch", home.location().getPitch());
    }

    public YamlConfiguration getConfiguration(Player player){
        return YamlConfiguration.loadConfiguration(new File(this.homeFolder, player.getUniqueId().toString()));
    }
}
