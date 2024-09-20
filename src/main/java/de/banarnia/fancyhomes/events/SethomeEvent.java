package de.banarnia.fancyhomes.events;

import de.banarnia.api.events.BanarniaEvent;
import de.banarnia.fancyhomes.data.HomeData;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import java.util.UUID;

public class SethomeEvent extends BanarniaEvent implements Cancellable {

    @Getter
    private final Player player;
    @Getter
    private final UUID homeOwner;
    @Getter
    private final HomeData homeData;
    @Getter
    private final String homeName;
    @Getter
    private final Location location;

    private boolean cancelled;

    public SethomeEvent(Player player, HomeData homeData, String homeName, Location location) {
        this.player = player;
        this.homeOwner = homeData.getUuid();
        this.homeData = homeData;
        this.homeName = homeName;
        this.location = location;
    }

    /**
     * Check if the teleported player is the owner of the home.
     * @return True if the player is the homeowner, else false.
     */
    public boolean isHomeOwner() {
        return player.getUniqueId().equals(homeOwner);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
