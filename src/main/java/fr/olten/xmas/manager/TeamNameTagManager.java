package fr.olten.xmas.manager;

import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import net.valneas.account.rank.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Manage the team name tag
 */
public class TeamNameTagManager {

    /**
     * The main scoreboard.
     */
    private static final Scoreboard SCOREBOARD = Bukkit.getScoreboardManager().getMainScoreboard();

    /**
     * Create a new scoreboard team for each rank.
     * @param rank The rank to create the team for.
     */
    public static void init(RankUnit rank){
        var team = SCOREBOARD.registerNewTeam(rank.getName());
        team.setPrefix(rank.getPrefix());
        team.setColor(rank.getColor());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
    }

    /**
     * Remove all the teams.
     */
    public static void reset(){
        for(var team : SCOREBOARD.getTeams()){
            team.unregister();
        }
    }

    /**
     * Update the team of a player.
     * @param player The player to update.
     */
    public static void update(Player player){
        var accountSystem = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");
        var accountManager = new AccountManager(accountSystem, player);
        update(accountManager);
    }

    /**
     * Update the team of a player.
     * @param accountManager The account manager of the player.
     */
    public static void update(AccountManager accountManager){
        var rank = accountManager.newRankManager().getMajorRank();
        var team = SCOREBOARD.getTeam(rank.getName());
        team.addEntry(accountManager.getName());
    }
}
