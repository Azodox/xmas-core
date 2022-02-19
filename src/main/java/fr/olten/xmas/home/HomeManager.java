package fr.olten.xmas.home;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HomeManager {

    private final static Logger LOGGER = Logger.getLogger("HomeManager");
    private static File homeFolder;

    public void setup(Plugin plugin){
        LOGGER.info("Starting setup (Plugin: " + plugin.getName() + ")");
        homeFolder = new File(plugin.getDataFolder(), "homes");

        if (!homeFolder.exists()) {
            LOGGER.info("Homes folder doesn't exist, creating it...");
            homeFolder.mkdirs();
            LOGGER.info("Homes folder created!");
        }
        LOGGER.info("Setup completed!");
    }

    public void init(Player player) throws IOException {
        File file = new File(homeFolder, player.getUniqueId() + ".yml");

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

        conf.set("homes." + home.name() + ".x", home.location().getX());
        conf.set("homes." + home.name() + ".y", home.location().getY());
        conf.set("homes." + home.name() + ".z", home.location().getZ());
        conf.set("homes." + home.name() + ".world_name", home.location().getWorld().getName());
        conf.set("homes." + home.name() + ".yaw", home.location().getYaw());
        conf.set("homes." + home.name() + ".pitch", home.location().getPitch());

        try {
            conf.save(getFile(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(UUID uuid, String homeName){
        YamlConfiguration conf = getConfiguration(uuid);
        conf.set("homes." + homeName, null);

        try {
            conf.save(getFile(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Home> getHomes(UUID uuid){
        YamlConfiguration conf = getConfiguration(uuid);
        ConfigurationSection section = conf.getConfigurationSection("homes");
        if(section == null){
            return Set.of();
        }
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

    public Optional<Home> getHome(UUID uuid, String homeName){
        return getHomes(uuid).stream().filter(h -> h.name().equals(homeName)).findAny();
    }

    public YamlConfiguration getConfiguration(UUID uuid){
        return YamlConfiguration.loadConfiguration(getFile(uuid));
    }

    public File getFile(UUID uuid){
        return new File(homeFolder, uuid + ".yml");
    }

    public void save(UUID uuid){
        try {
            getConfiguration(uuid).save(getFile(uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UUID getUUIDByName(String name){
        for(File file : homeFolder.listFiles()){
            FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
            if(conf.getString("name").equals(name)){
                return UUID.fromString(file.getName().replace(".yml", ""));
            }
        }
        return null;
    }
}
