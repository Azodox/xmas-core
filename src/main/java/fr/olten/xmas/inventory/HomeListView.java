package fr.olten.xmas.inventory;

import fr.olten.xmas.Core;
import fr.olten.xmas.home.Home;
import fr.olten.xmas.util.HeadUtil;
import fr.olten.xmas.util.ItemBuilder;
import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class HomeListView extends PaginatedView<Home> {

    private final Core core;
    private final OfflinePlayer target;

    public HomeListView(Core core, int size, String title, List<Home> homes, OfflinePlayer target) {
        super(size, title);
        this.core = core;
        this.target = target;

        setLayout(
                "OOOOOOOOO",
                "OOOOOOOOO",
                "OOOOOOOOO",
                "XXX<I>XXX"
        );
        setSource(homes);
        setCancelOnDrag(true);
        setCancelOnDrop(true);
        setCancelOnMoveOut(true);
        setCancelOnPickup(true);
        setCancelOnClone(true);
        setCancelOnShiftClick(true);
        setCancelOnClick(true);

        setPreviousPageItem((context, item) -> item.withItem(new ItemBuilder(HeadUtil.getHead("previousPage")).setName("§c§lPage précédente").build()));
        setNextPageItem((context, item) -> item.withItem(new ItemBuilder(HeadUtil.getHead("nextPage")).setName("§c§lPage suivante").build()));
    }

    @Override
    protected void onItemRender(@NotNull PaginatedViewSlotContext<Home> context, @NotNull ViewItem viewItem, @NotNull Home value) {
        var list = HeadUtil.getTranslator().keySet().stream().filter(s -> s.startsWith("birdhouse-")).toList();
        viewItem.withItem(new ItemBuilder(HeadUtil.getHead(list.get(new Random().nextInt(list.size()))).clone()).setName(ChatColor.GOLD + value.name()).build())
                .onClick(click -> this.core.getHomeManager().getHome(target.getUniqueId(), value.name()).ifPresent(home -> {
                    click.getPlayer().teleport(home.location());
                    click.getPlayer().sendActionBar(Component.text(ChatColor.AQUA + "Téléportation vers " + ChatColor.BOLD + target.getName() + "/" + home.name() + ChatColor.AQUA + "..."));
                })).closeOnClick();
    }
}
