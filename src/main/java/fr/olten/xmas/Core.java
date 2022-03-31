package fr.olten.xmas;

import fr.olten.xmas.commands.DelHomeCommand;
import fr.olten.xmas.commands.HomeCommand;
import fr.olten.xmas.commands.HomesCommand;
import fr.olten.xmas.commands.SetHomeCommand;
import fr.olten.xmas.commands.arguments.OfflinePlayerArgument;
import fr.olten.xmas.home.HomeManager;
import fr.olten.xmas.listener.InventoryClickListener;
import fr.olten.xmas.listener.PlayerChatListener;
import fr.olten.xmas.listener.PlayerJoinListener;
import fr.olten.xmas.listener.RankChangedListener;
import fr.olten.xmas.manager.TeamNameTagManager;
import fr.olten.xmas.util.HeadUtil;
import fr.olten.xmas.util.ScrollerInventory;
import io.github.llewvallis.commandbuilder.CommandBuilder;
import io.github.llewvallis.commandbuilder.DefaultInferenceProvider;
import io.github.llewvallis.commandbuilder.ReflectionCommandCallback;
import net.valneas.account.rank.RankUnit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
    Welcome to 2022 xmas event's core.
    Here is the plugin center of the event.
    In this plugin, you can find the basic systems of the event,
    such as homes, teleportation, automatic messages and all general functionnalities.
*/
public class Core extends JavaPlugin {

    public static final String CHAT_FORMAT = "%rank%" + "%1$s " + ChatColor.DARK_GRAY + "» " + ChatColor.RESET + "%2$s";

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

        Arrays.stream(RankUnit.values()).forEach(TeamNameTagManager::init);
        getServer().getOnlinePlayers().forEach(TeamNameTagManager::update);

        registerCommands();
        /*
            Register every single listener needed.
        */
        registerEvents(
                new PlayerJoinListener(this),
                new ScrollerInventory.ScrollerInventoryListener(),
                new InventoryClickListener(this),
                new PlayerChatListener(this),
                new RankChangedListener()
        );
        /*
            Register with HeadUtil.java all needed mc heads.
        */
        //registerHeads();

        getLogger().info("Enabled!");
    }


    @Override
    public void onDisable() {
        TeamNameTagManager.reset();
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
        this.headUtil.addHead("75465f77e7fd4217384998c33859ad9636e1893bee405dd2519423b51767", "birdhouse-red");
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }
}
