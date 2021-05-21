package com.aimine.slideshowPlugin;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Ref {
	public static final String PREFIX            = colour("&8[&aEduCraft&8] &f");
	public static final String PERMISSION_CREATE = "imagedisplays.create";
	public static final String PERMISSION_REMOVE = "imagedisplays.remove";
	public static File bannerFolder;
	public static NamespacedKey remoteIdentifier;
	public static ItemStack forwardRemote;
	public static NamespacedKey bRemoteIdentifier;
	public static ItemStack backwardsRemote;
	
	public static String colour(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
	public static void init() {
		remoteIdentifier = new NamespacedKey(SlideshowPlugin.getInstance(), "isForwardRemote");
		bRemoteIdentifier = new NamespacedKey(SlideshowPlugin.getInstance(), "isBackwardsRemote");
		
		forwardRemote = new ItemStack(Material.DIAMOND);
		ItemMeta meta = forwardRemote.getItemMeta();
		meta.setDisplayName(colour("&a&lForward Remote"));
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		meta.getPersistentDataContainer().set(remoteIdentifier, PersistentDataType.BYTE, (byte)0x01);
		forwardRemote.setItemMeta(meta);
		
		backwardsRemote = new ItemStack(Material.DIAMOND);
		ItemMeta bmeta = forwardRemote.getItemMeta();
		bmeta.setDisplayName(colour("&a&lBackwards Remote"));
		bmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		bmeta.getPersistentDataContainer().set(bRemoteIdentifier, PersistentDataType.BYTE, (byte)0x01);
		backwardsRemote.setItemMeta(bmeta);
	}
}