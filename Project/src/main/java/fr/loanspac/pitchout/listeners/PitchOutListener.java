package fr.loanspac.pitchout.listeners;

import fr.loanspac.pitchout.game.GameType;
import fr.loanspac.pitchout.utils.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PitchOutListener implements Listener {
    private ItemStack pelle = ItemManager.getItem(Material.DIAMOND_SPADE, "§cPelle Poussoire", true, true);

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(GameType.getCurrentState().equals(GameType.PITCHOUT))) return;

        Entity damagedE = event.getEntity();
        Entity damagerE = event.getDamager();

        if(!(damagedE instanceof Player)) return;

        Player damaged = (Player) event.getEntity();

        if(!(damagerE instanceof Player)) return;

        Player damager = (Player) event.getDamager();

        if(damager.getMainHand().equals(pelle)) {
            knockBack(damaged, damager);
        }
    }

    private void knockBack(Player damaged, Player damager) {
        double pitchKb = 200d;
        double multiplier = Math.min(10, 4 / 7.5 + (Math.exp(0.0072 * (pitchKb - 950)) * (Math.pow(4, 0.59) * Math.pow(pitchKb, 1.3))));

        Vector direction = damager.getEyeLocation().getDirection();
        direction.setY(((direction.getY() >= 0.45 || direction.getY() <= -0.5) ? direction.getY() : 0.20))
                .normalize()
                .setX(direction.getX() * multiplier)
                .setZ(direction.getZ() * multiplier)
                .setY(direction.getY() * (multiplier / 1.5));

        damaged.setVelocity(direction);
    }


}
