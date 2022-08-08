package fr.olten.xmas;

import co.aikar.commands.PaperCommandManager;
import fr.olten.xmas.commands.DelHomeCommand;
import fr.olten.xmas.commands.HomeCommand;
import fr.olten.xmas.commands.HomesCommand;
import fr.olten.xmas.commands.SetHomeCommand;
import fr.olten.xmas.commands.resolver.OfflinePlayerResolver;
import fr.olten.xmas.home.HomeManager;
import fr.olten.xmas.listener.PlayerChatListener;
import fr.olten.xmas.listener.PlayerJoinListener;
import fr.olten.xmas.listener.RankChangedListener;
import fr.olten.xmas.manager.TeamNameTagManager;
import fr.olten.xmas.util.HeadUtil;
import net.valneas.account.AccountSystemApi;
import net.valneas.account.lib.eventbus.EventBus;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
    Welcome to 2022 xmas event's core.
    Here is the plugin center of the event.
    In this plugin, you can find the basic systems of the event,
    such as homes, teleportation, automatic messages and all general functionalities.
*/
public class Core extends JavaPlugin {

    private HeadUtil headUtil;
    private final HomeManager homeManager = new HomeManager();

    /**
        This method is executed when the plugin starts.
        This is where we initialize everything. (e.g class' instance, call other methods)

        When everything's done, we can print it in the console.
    */
    @Override
    public void onEnable() {
        this.homeManager.setup(this);
        this.headUtil = new HeadUtil();

        /*
            Register with HeadUtil.java all needed mc heads.
        */
        registerHeads();

        var provider = getServer().getServicesManager().getRegistration(AccountSystemApi.class);
        if(provider != null) {
            var accountSystem = provider.getProvider();
            accountSystem.getRankHandler().getAllRanksQuery().stream().forEach(TeamNameTagManager::init);
            getServer().getPluginManager().registerEvents(new PlayerChatListener(this, accountSystem), this);
        }
        getServer().getOnlinePlayers().forEach(TeamNameTagManager::update);
        registerCommands();
        /*
            Register every single listener needed.
        */
        registerEvents(
                new PlayerJoinListener(this)
        );

        EventBus.getDefault().register(new RankChangedListener());

        getLogger().info("Enabled!");
    }

    @Override
    public void onDisable() {
        TeamNameTagManager.reset();
        HeadUtil.getHeads().clear();
    }

    /**
        This will simplify the way of registering multiple listeners.
    */
    private void registerEvents(Listener...listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    /**
        Register commands.
    */
    private void registerCommands() {
        var commandManager = new PaperCommandManager(this);
        commandManager.getCommandContexts().registerContext(OfflinePlayer.class, new OfflinePlayerResolver());
        commandManager.getCommandCompletions().registerCompletion("target", c -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
        commandManager.registerCommand(new DelHomeCommand(this));
        commandManager.registerCommand(new HomesCommand(this));
        commandManager.registerCommand(new HomeCommand(this));
        commandManager.registerCommand(new SetHomeCommand(this));
    }

    private void registerHeads(){
        this.headUtil.addHead("333ae8de7ed079e38d2c82dd42b74cfcbd94b3480348dbb5ecd93da8b81015e3", "nextPage");
        this.headUtil.addHead("81c96a5c3d13c3199183e1bc7f086f54ca2a6527126303ac8e25d63e16b64ccf", "previousPage");
        this.headUtil.addHead("d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f", "information");

        /* Bird houses */
        this.headUtil.addHead("d68c95c89ebe1b5a89b9e51bb21a5cc7379bbf5b109d8b517713e772f28f379", "birdhouse-orange");
        this.headUtil.addHead("123daf8c965e5034c4bfc79d2d6ee8339f3a08a7e62bc56b7ab8eb2d7d070", "birdhouse-magenta");
        this.headUtil.addHead("b0f1afd3e65e622346691748b1d7a5bb39c4fd6b1ef28af5f338545b2571c", "birdhouse-brown");
        this.headUtil.addHead("efe24c6f52ddc08359f792301eca96aecf66979dc899c43242d2587ebcb70e6", "birdhouse-lime");
        this.headUtil.addHead("cdaea3e13a513da1051ea59d17edfb03e8d8388ede88888476dfb693f2c8398", "birdhouse-cyan");
        this.headUtil.addHead("4e338bb32287afd632bc874b32f53a56f44b7aee26cd3d5c6fdad99f340f470", "birdhouse-purple");
        this.headUtil.addHead("24fbe85787c12787ddceb852d7142dae907578a272cc2021555ba805d227ea7", "birdhouse-yellow");
        this.headUtil.addHead("fc1bd7ce2d527ec6f76b8e81f19d69d1b9ff4d5b6cd6cc764eed1945a6c6c", "birdhouse-green");
        this.headUtil.addHead("75465f77e7fd4217384998c33859ad9636e1893bee405dd2519423b51767", "birdhouse-red");
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }
}
