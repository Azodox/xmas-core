package fr.olten.xmas;

import fr.olten.xmas.commands.DelHomeCommand;
import fr.olten.xmas.commands.HomeCommand;
import fr.olten.xmas.commands.HomesCommand;
import fr.olten.xmas.commands.SetHomeCommand;
import fr.olten.xmas.commands.arguments.OfflinePlayerArgument;
import fr.olten.xmas.home.HomeManager;
import fr.olten.xmas.listener.InventoryClickListener;
import fr.olten.xmas.listener.PlayerJoinListener;
import fr.olten.xmas.util.HeadUtil;
import fr.olten.xmas.util.ScrollerInventory;
import io.github.llewvallis.commandbuilder.CommandBuilder;
import io.github.llewvallis.commandbuilder.DefaultInferenceProvider;
import io.github.llewvallis.commandbuilder.ReflectionCommandCallback;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
    Welcome to 2022 xmas event's core.
    Here is the plugin center of the event.
    In this plugin, you can find the basic systems of the event,
    such as homes, teleportation, automatic messages and all general functionnalities.
*/
public class Core extends JavaPlugin {

    private HeadUtil headUtil;
    private final HomeManager homeManager = new HomeManager();

    /**
        This method is executed when the plugin starts.
        This is where we initalize everything. (e.g class' instance, call other methods)

        When everything's done, we can print it in the console.
    */
    @Override
    public void onEnable() {
        this.homeManager.setup(this);
        this.headUtil = new HeadUtil();
        
        registerCommands();
        /*
            Register every single listener needed.
        */
        registerEvents(
                new PlayerJoinListener(this),
                new ScrollerInventory.ScrollerInventoryListener(),
                new InventoryClickListener(this)
        );
        /*
            Register with HeadUtil.java all needed mc heads.
        */
        registerHeads();

        getLogger().info("Enabled!");
    }

    /**
        This will simplify the way of registering multiple listeners.
    */
    private void registerEvents(Listener...listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /*
        Register commands.
        @link https://github.com/LlewVallis/command-builder
    */
    private void registerCommands() {
        DefaultInferenceProvider.getGlobal().register(OfflinePlayer.class, new OfflinePlayerArgument());
        new CommandBuilder().infer(new HomeCommand(this)).build(new ReflectionCommandCallback(new HomeCommand(this)), getCommand("home"));
        new CommandBuilder().infer(new SetHomeCommand(this)).build(new ReflectionCommandCallback(new SetHomeCommand(this)), getCommand("sethome"));
        new CommandBuilder().infer(new DelHomeCommand(this)).build(new ReflectionCommandCallback(new DelHomeCommand(this)), getCommand("delhome"));
        new CommandBuilder().infer(new HomesCommand(this)).build(new ReflectionCommandCallback(new HomesCommand(this)), getCommand("homes"));
    }

    private void registerHeads(){
        this.headUtil.addHead("333ae8de7ed079e38d2c82dd42b74cfcbd94b3480348dbb5ecd93da8b81015e3", "nextPage");
        this.headUtil.addHead("81c96a5c3d13c3199183e1bc7f086f54ca2a6527126303ac8e25d63e16b64ccf", "previousPage");
        this.headUtil.addHead("d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f", "information");
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }
}
