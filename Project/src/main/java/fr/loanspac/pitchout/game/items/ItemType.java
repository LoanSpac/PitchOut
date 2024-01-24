package fr.loanspac.pitchout.game.items;

import fr.loanspac.pitchout.utils.ItemManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum ItemType {
    PELLE(ItemManager.getItem(Material.DIAMOND_SPADE, "§cPelle Poussoire", true, true)),
    ARC(ItemManager.getItem(Material.BOW, "§cArc Poussoire", Enchantment.ARROW_INFINITE, 1, true));

    private final ItemStack item;

    ItemType(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
