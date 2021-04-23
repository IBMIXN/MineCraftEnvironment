package com.meeku.tutorialPlugin;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Ref {
	
	public static final String PREFIX            = color("&8[&aEduCraft&8] &f");
	public static final String PERMISSION_CREATE = "imagedisplays.create";
	public static final String PERMISSION_REMOVE = "imagedisplays.remove";
	public static File bannerFolder;
	public static NamespacedKey remoteIdentifier;
	public static ItemStack forwardRemote;
	
	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
	public static void init() {
		bannerFolder = new File("plugins/" + SpigotBlankPlugin.getInstance().getName() + "/displays");
		bannerFolder.mkdirs();
		
		remoteIdentifier = new NamespacedKey(SpigotBlankPlugin.getInstance(), "isForwardRemote");
		
		forwardRemote = new ItemStack(Material.DIAMOND);
		ItemMeta meta = forwardRemote.getItemMeta();
		meta.setDisplayName(color("&a&lForward Remote"));
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		meta.getPersistentDataContainer().set(remoteIdentifier, PersistentDataType.BYTE, (byte)0x01);
		forwardRemote.setItemMeta(meta);
	}
}