package me.blha303;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockTracker extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		event.setCancelled(true);
		Collection<ItemStack> drops = event.getBlock().getDrops();
		Location loc = event.getBlock().getLocation();
		List<String> newlore = new ArrayList<String>();
		newlore.add("Broken by: " + event.getPlayer().getName());
//		newlore.add("at " + loc.getBlockX() + ":" + loc.getBlockY() + ":"
//				+ loc.getBlockZ());
		loc.getBlock().setType(Material.AIR);
		for (ItemStack i : drops) {
			ItemMeta im = i.getItemMeta();
			im.setLore(newlore);
			i.setItemMeta(im);
			loc.getWorld().dropItemNaturally(loc, i);
		}
	}
}
