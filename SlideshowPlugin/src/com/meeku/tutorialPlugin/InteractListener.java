package com.meeku.tutorialPlugin;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import com.tek.idisplays.Reference;
import com.tek.idisplays.Selection;
import com.tek.idisplays.map.DisplayManager;
import com.tek.idisplays.tasks.SelectionHighlightTask;

public class InteractListener implements Listener {
	
	
	private static Selection selectTwo;
	private static int count = 0;
	private String[] websites = {
			"Slide1.jpg",
			"Slide2.jpg",
			"Slide3.jpg",
			"Slide4.jpg",
			"Slide5.jpg",
			"Slide6.jpg",
			"Slide7.jpg",
	};
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) {
				final Player player = event.getPlayer();
				if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().getPersistentDataContainer().has(Ref.remoteIdentifier, PersistentDataType.BYTE)) {
					Block baseBlock = selectTwo.getFrom();
					for (Entity e : baseBlock.getWorld().getNearbyEntities(baseBlock.getLocation(), 2, 2, 2)) {
						ItemFrame frame = (ItemFrame) e;
						DisplayManager.attemptDisplayDeletion(frame, player);
						selectTwo.setImageName(websites[count]);
						DisplayManager.attemptCreateDisplay(selectTwo, player);
						if (count == websites.length - 1) {
							count = 0;
							break;
						}
						else {
							count++;
							break;
						}
					}
				}
			}
		}
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) {
				final Player player = event.getPlayer();
				if(SpigotBlankPlugin.getInstance().getSelections().containsKey(player.getUniqueId())) {
					if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().getPersistentDataContainer().has(Reference.wandIdentifier, PersistentDataType.BYTE)) {
						Selection selection = SpigotBlankPlugin.getInstance().getSelections().get(player.getUniqueId());
						if(selection.getFrom() == null) {
							Block blockLook = player.getTargetBlockExact(5);
							BlockFace faceLooking = SelectionHighlightTask.getBlockFace(player);
							
							if(faceLooking == BlockFace.UP || faceLooking == BlockFace.DOWN) {
								player.sendMessage(Reference.PREFIX + Reference.color("&cYou cannot place displays on the floor or ceiling"));
							} else {
								selection.setFrom(blockLook.getRelative(faceLooking));
								selection.setFace(faceLooking);
								player.sendMessage(Reference.PREFIX + Reference.color("&aFirst display corner location set"));
							}
						} else {
							Block blockLook = player.getTargetBlockExact(5);
							BlockFace faceLooking = SelectionHighlightTask.getBlockFace(player);
							if(faceLooking == null || blockLook == null) return;
							Block fromBlock = selection.getFrom();
							Block toBlock = blockLook.getRelative(faceLooking);
							
							boolean works = true;
							if(selection.getFace() == BlockFace.WEST || selection.getFace() == BlockFace.EAST) if(Math.abs(toBlock.getX() - fromBlock.getX()) != 0) works = false;
							if(selection.getFace() == BlockFace.SOUTH || selection.getFace() == BlockFace.NORTH) if(Math.abs(toBlock.getZ() - fromBlock.getZ()) != 0) works = false;
						
							if(works) {
								selection.setTo(toBlock);
								player.getInventory().setItemInMainHand(null);
								player.sendMessage(Reference.PREFIX + Reference.color("&aSelection completed! Creating display..."));
								
								selectTwo = selection;
								player.sendMessage("selectTwo set");
								DisplayManager.attemptCreateDisplay(selection, player);
								player.sendMessage("Display Created");
								
								player.getInventory().addItem(Ref.forwardRemote);
								player.sendMessage("added remote");
								player.sendMessage(Ref.PREFIX + Ref.color("&aGiven you the forward remote"));
								player.sendMessage(Ref.color("&7- &6Right click to move to the next slide"));
								
								SpigotBlankPlugin.getInstance().getSelections().remove(player.getUniqueId());
							} else {
								player.sendMessage(Reference.PREFIX + Reference.color("&cYou cannot place a display at this location (Must be 1 block thick)"));
							}
						}
					}
				}
			}
		}
	}
	public static Selection getSelection() {
		return selectTwo;
	}
	
}
