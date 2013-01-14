package me.blha303;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockTracker extends JavaPlugin implements Listener {

	// Cheers to einand and hdon ;)

	public void onEnable() {
//		getConfig().addDefault("automaticUpdates", true);
//		getConfig().options().copyDefaults(true);
//		saveConfig();
//		if (getConfig().getBoolean("automaticUpdates")) {
//			Updater updater = new Updater(this, "blocktracker", this.getFile(), Updater.UpdateType.DEFAULT, false);
//			Updater.UpdateResult result = updater.getResult();
//			switch (result) {
//			case SUCCESS:
//				this.getLogger().info("New update for BlockTracker was found!");
//				this.getLogger().info("Downloaded successfully!");
//				this.getLogger().info("BlockTracker will be updated next time you restart or reload the server.");
//				break;
//			case FAIL_DOWNLOAD:
//				this.getLogger().info("There is an update for BlockTracker, but I can't download it for some reason.");
//				this.getLogger().info("Please go to http://dev.bukkit.org/server-mods/blocktracker to get the update.");
//				break;
//			case FAIL_DBO:
//				this.getLogger().info("Unable to check for updates.");
//				break;
//			default:
//				break;
//			}
//		} else {
//			Updater updater = new Updater(this, "blocktracker", this.getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
//			Updater.UpdateResult result = updater.getResult();
//			switch(result) {
//			case UPDATE_AVAILABLE:
//				this.getLogger().info("There is an update available, but you've disabled automatic updates.");
//				break;
//			default:
//				break;
//			}
//		}
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE
				|| event.getPlayer().getItemInHand()
						.containsEnchantment(Enchantment.SILK_TOUCH)) {
			return;
		}
		event.setCancelled(true);
		String brokenwith = event.getPlayer().getItemInHand().getType().toString()
				.toLowerCase().replaceAll("air", "their hand");
		Location loc = event.getBlock().getLocation();
		List<String> newlore = new ArrayList<String>();
		newlore.add("Broken by: " + event.getPlayer().getName() + " with "
				+ brokenwith);
		// newlore.add("at " + loc.getBlockX() + ":" + loc.getBlockY() + ":"
		// + loc.getBlockZ());
		Collection<ItemStack> drops = event.getBlock().getDrops(
				event.getPlayer().getItemInHand());
		loc.getBlock().setType(Material.AIR);
		for (ItemStack i : drops) {
			ItemMeta im = i.getItemMeta();
			im.setLore(newlore);
			i.setItemMeta(im);
			loc.getWorld().dropItemNaturally(loc, i);
		}
		return;
	}
}