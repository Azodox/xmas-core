package fr.olten.xmas;

import fr.olten.xmas.commands.HomeCommand;
import fr.olten.xmas.commands.arguments.OfflinePlayerArgument;
import fr.olten.xmas.home.HomeManager;
import fr.olten.xmas.listener.PlayerJoinListener;
import io.github.llewvallis.commandbuilder.CommandBuilder;
import io.github.llewvallis.commandbuilder.DefaultInferenceProvider;
import io.github.llewvallis.commandbuilder.ReflectionCommandCallback;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private final HomeManager homeManager = new HomeManager();

    @Override
    public void onEnable() {
        homeManager.setup(this);

        registerCommands();
        registerEvents(
                new PlayerJoinListener(this)
        );
        getLogger().info("Enabled!");
    }

    private void registerEvents(Listener...listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommands() {
        DefaultInferenceProvider.getGlobal().register(OfflinePlayer.class, new OfflinePlayerArgument());
        new CommandBuilder()
                .infer(new HomeCommand(this))
                .build(new ReflectionCommandCallback(new HomeCommand(this)), getCommand("home"));
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }
}
