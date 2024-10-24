package de.banarnia.fancyhomes.gui;

import de.banarnia.api.triumphgui.builder.item.ItemBuilder;
import de.banarnia.api.triumphgui.components.util.Legacy;
import de.banarnia.api.triumphgui.guis.Gui;
import de.banarnia.api.triumphgui.guis.GuiItem;
import de.banarnia.api.triumphgui.guis.PaginatedGui;
import de.banarnia.fancyhomes.FancyHomes;
import de.banarnia.fancyhomes.FancyHomesAPI;
import de.banarnia.api.UtilGUI;
import de.banarnia.api.UtilItem;
import de.banarnia.api.UtilThread;
import de.banarnia.fancyhomes.data.HomeData;
import de.banarnia.fancyhomes.data.storage.Home;
import de.banarnia.fancyhomes.lang.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeGUI {

    private final HomeData data;
    private final OfflinePlayer target;
    private final PaginatedGui gui;

    public HomeGUI(String title, HomeData data) {
        this.data = data;
        this.target = Bukkit.getOfflinePlayer(data.getUuid());
        this.gui = Gui.paginated().title(Legacy.SERIALIZER.deserialize(title)).rows(2).pageSize(9).create();
        this.gui.setDefaultClickAction(event -> event.setCancelled(true));
        this.gui.setOpenGuiAction(event -> init());
    }

    private void init() {
        gui.clearPageItems();

        gui.getFiller().fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem());
        gui.setItem(gui.getRows(), 5, getInfoItem());

        List<String> homeNamesSorted = data.getHomeMap().keySet().stream().sorted().collect(Collectors.toCollection(LinkedList::new));
        for (String homeName : homeNamesSorted) {
            Home home = data.getHome(homeName);
            gui.addItem(getHomeItem(home));
        }

        UtilGUI.setPaginationItems(gui, Message.GUI_HOME_PAGE_PREVIOUS.get(), Message.GUI_HOME_PAGE_NEXT.get());
        gui.update();
    }

    private GuiItem getInfoItem() {
        ItemBuilder builder = ItemBuilder.from(UtilItem.getPlayerSkull(target));
        builder.setName("§e" + target.getName());

        int limit = FancyHomesAPI.get().getHomeLimit(target.getUniqueId());
        builder.setLore("§7Homes: §e" + data.getHomeMap().size() + (limit >= 0 ? "§7/§a" + limit : ""));

        return builder.asGuiItem();
    }

    private GuiItem getHomeItem(Home home) {
        GuiItem guiItem = new GuiItem(home.buildIcon());
        guiItem.setAction(click -> {
            Player player = (Player) click.getWhoClicked();
            if (click.isLeftClick()) {
                if (click.isShiftClick()) {
                    new MaterialSelectionGUI(Message.GUI_ICON_SELECTION_TITLE.get(), Material.getMaterial(home.getIcon()),
                            Message.GUI_SAVE_NAME.get(), Message.GUI_CANCEL_NAME.get(), material -> {
                        if (material == null || material.toString() == home.getIcon()) {
                            open(player);
                            return;
                        }

                        data.updateHome(home.getName(), material.toString())
                                .thenAccept(success -> UtilThread.runSync(FancyHomes.getInstance(), () -> {
                                    if (!success)
                                        player.sendMessage(Message.GUI_ICON_UPDATE_FAILED.get());

                                    return open(player);
                                }));
                    }).open(player);
                    return;
                }

                FancyHomesAPI.get().teleport(player, home);
                return;
            }

            new ConfirmationGUI(Message.GUI_CONFIRMATION_TITLE.get(), delete -> {
                if (delete) {
                    FancyHomesAPI.get().deleteHome(player, target.getUniqueId(), home.getName())
                            .thenRun(() -> UtilThread.runSync(FancyHomes.getInstance(), () -> open(player)));
                } else
                    gui.open(player);
            }).open(player);
        });

        return guiItem;
    }

    public boolean open(Player player) {
        gui.open(player);
        return true;
    }

}
