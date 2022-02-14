package fr.olten.xmas.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public void init(Player player) throws IOException {
        File file = new File(this.homeFolder, player.getUniqueId() + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
        conf.addDefault("name", player.getName());
        conf.options().copyDefaults(true);
        conf.save(file);
    }

    public void checkName(Player player){
        YamlConfiguration conf = getConfiguration(player.getUniqueId());
        if(!conf.getString("name").equals(player.getName())){
            conf.set("name", player.getName());
            try {
                conf.save(getFile(player.getUniqueId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean exists(UUID uuid){
        return getFile(uuid).exists();
    }

    public void add(UUID uuid, Home home) {
        //TODO : add logs
        YamlConfiguration conf = getConfiguration(uuid);
        List<String> homes = conf.getStringList("homes");
        //homes.add(home.name());

        conf.set("homes." + home.name() + ".x", home.location().getX());
        conf.set("homes." + home.name() + ".y", home.location().getY());
        conf.set("homes." + home.name() + ".z", home.location().getZ());
        conf.set("homes." + home.name() + ".world_name", home.location().getWorld().getName());
        conf.set("homes." + home.name() + ".yaw", home.location().getYaw());
        conf.set("homes." + home.name() + ".pitch", home.location().getPitch());

        //conf.set("homes", homes);
        try {
            conf.save(getFile(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Home> getHomes(UUID uuid){
        YamlConfiguration conf = getConfiguration(uuid);
        ConfigurationSection section = conf.getConfigurationSection("homes");
        return section.getKeys(false).stream().map(h -> new Home(h,
                new Location(
                        Bukkit.getWorld(conf.getString("homes." + h + ".world_name")),
                        conf.getDouble("homes." + h + ".x"),
                        conf.getDouble("homes." + h + ".y"),
                        conf.getDouble("homes." + h + ".z"),
                        (float) conf.getDouble("homes." + h + ".yaw"),
                        (float) conf.getDouble("homes." + h + ".pitch")
                )
        )).collect(Collectors.toSet());
    }

    public YamlConfiguration getConfiguration(UUID uuid){
        return YamlConfiguration.loadConfiguration(getFile(uuid));
    }

    public File getFile(UUID uuid){
        return new File(this.homeFolder, uuid + ".yml");
    }

    public void save(UUID uuid){
        try {
            getConfiguration(uuid).save(getFile(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
