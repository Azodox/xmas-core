package fr.olten.xmas.manager;

import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import net.valneas.account.rank.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamNameTagManager {

    private static final Scoreboard SCOREBOARD = Bukkit.getScoreboardManager().getMainScoreboard();

    public static void init(RankUnit rank){
        var team = SCOREBOARD.registerNewTeam(rank.getName());
        team.setPrefix(rank.getPrefix());
        team.setColor(rank.getColor());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
    }

    public static void reset(){
        for(var team : SCOREBOARD.getTeams()){
            team.unregister();
        }
    }


    public static void update(Player player){
        var accountSystem = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");
        var accountManager = new AccountManager(accountSystem, player);
        update(accountManager);
    }

    public static void update(AccountManager accountManager){
        var rank = accountManager.newRankManager().getMajorRank();
        var team = SCOREBOARD.getTeam(rank.getName());
        team.addEntry(accountManager.getName());
    }
}
