package fr.olten.xmas;

import fr.olten.xmas.commands.HomeCommand;
import fr.olten.xmas.commands.arguments.OfflinePlayerArgument;
import io.github.llewvallis.commandbuilder.CommandBuilder;
import io.github.llewvallis.commandbuilder.DefaultInferenceProvider;
import io.github.llewvallis.commandbuilder.ReflectionCommandCallback;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
        getLogger().info("Enabled!");
    }

    private void registerCommands() {
        DefaultInferenceProvider.getGlobal().register(OfflinePlayer.class, new OfflinePlayerArgument());
        new CommandBuilder()
                .infer(new HomeCommand())
                .build(new ReflectionCommandCallback(new HomeCommand()), getCommand("home"));
    }
}
